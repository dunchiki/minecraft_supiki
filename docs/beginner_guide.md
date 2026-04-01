# Mod作成初心者向けガイド：Supiki Entityの実装

このドキュメントでは、今回のPRで行った「SupikiEntityの追加」を題材に、Minecraft Forge Mod開発の基礎的な概念をやさしく説明します。

---

## 1. Entityとは何か？

Minecraft の「Entity（エンティティ）」は、ワールドに存在する動くオブジェクト（Mob、プレイヤー、矢など）の総称です。
今回追加した `SupikiEntity` は、ゲーム内でスポーンして動き回る友好的なMobです。

---

## 2. なぜ `PathfinderMob` を継承するのか？

Minecraft には Mob 実装のための抽象基底クラスがいくつかあります。

| クラス | 用途 |
|---|---|
| `Entity` | すべてのエンティティの基底（動けない物体含む） |
| `LivingEntity` | HP・体力を持つエンティティ |
| `Mob` | AI を持つ汎用Mob |
| `PathfinderMob` | **経路探索（Pathfinding）AIを持つ地上Mob**（推奨） |
| `Animal` | 繁殖できる友好Mob |

このプロジェクトでは `PathfinderMob` を採用しています。これにより、地形に沿った経路探索や細かいAI目標（Goal）の設定が可能になります。

```java
// SupikiEntity は PathfinderMob を継承する
public class SupikiEntity extends PathfinderMob {
    // ...
}
```

---

## 3. `registerGoals()` とは？

`registerGoals()` は、Mob の「行動リスト（AI Goals）」を設定するメソッドです。
ゲームエンジンは優先度（小さい数字 = 高優先）を見ながら、Mob がどんな行動を取るか決定します。

```java
@Override
protected void registerGoals() {
    // 優先度0: 水に落ちたら泳ぐ（サバイバル行動）
    this.goalSelector.addGoal(0, new FloatGoal(this));

    // 優先度1: 水を避けながらランダムに歩き回る
    this.goalSelector.addGoal(1, new WaterAvoidingRandomStrollGoal(this, 1.0D));

    // 優先度2: 近くのプレイヤーを見る
    this.goalSelector.addGoal(2, new LookAtPlayerGoal(this, Player.class, 6.0F));

    // 優先度3: ぼーっとランダムな方向を向く
    this.goalSelector.addGoal(3, new RandomLookAroundGoal(this));
}
```

---

## 4. `DeferredRegister` と `RegistryObject` とは？

Minecraft Forge では、新しいブロック・アイテム・Entityなどをゲームに追加するとき、**専用の登録システム**（レジストリ）を通す必要があります。

`DeferredRegister` は「後で登録してください」という予約リストです。  
`RegistryObject` はその予約した登録オブジェクトへの参照です。

```java
// EntityType の登録リストを作成（MODIDを指定）
public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
    DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, SupikiMod.MODID);

// "supiki" という名前で SupikiEntity の EntityType を予約登録
public static final RegistryObject<EntityType<SupikiEntity>> SUPIKI =
    ENTITY_TYPES.register("supiki", () -> EntityType.Builder
        .of(SupikiEntity::new, MobCategory.CREATURE)
        .sized(0.45F, 0.7F)
        .build(ENTITY_TYPES.key("supiki")));
```

登録予約した `ENTITY_TYPES` はMod初期化時にイベントバスへ接続します：

```java
// SupikiMod のコンストラクタ内
ModEntities.ENTITY_TYPES.register(modBusGroup);
```

---

## 5. `EntityAttributeCreationEvent` とは？（属性登録）

EntityをMinecraftにスポーンさせるには、**EntityType の登録だけでなく「属性（Attributes）」の登録も必要**です。  
属性とは、体力（MaxHealth）・移動速度（MovementSpeed）・攻撃力などのパラメータです。

これを登録しないと、エンティティをスポーンさせた瞬間にゲームがクラッシュします。

```java
// SupikiMod のコンストラクタ内: EntityAttributeCreationEvent を購読
EntityAttributeCreationEvent.getBus(modBusGroup).addListener(SupikiMod::registerAttributes);

// ハンドラー: SupikiEntity の EntityType に属性を紐付ける
private static void registerAttributes(EntityAttributeCreationEvent event) {
    event.put(ModEntities.SUPIKI.get(), SupikiEntity.createAttributes().build());
}
```

`SupikiEntity.createAttributes()` は `Mob.createMobAttributes()` をベースにしており、基本的な体力・移動速度が含まれています。

---

## 6. なぜ `EntityAttributeCreationEvent.getBus(modBusGroup)` という書き方なのか？

Forge 61.1.0 以降では、イベントリスナーの登録方法が変わりました。

**旧パターン（使えない）:**
```java
modBusGroup.addListener(SupikiMod::registerAttributes); // コンパイルエラー
```

**新パターン（正しい）:**
```java
EntityAttributeCreationEvent.getBus(modBusGroup).addListener(SupikiMod::registerAttributes);
```

これは「このイベントが流れるバスを取得してから、そこにリスナーを登録する」という意味です。

---

## 7. パッケージ構成の重要性

ファイルをどのパッケージ（フォルダ）に置くかは、プロジェクトの整理に大切です。
このプロジェクトでは CONTRIBUTING.md に方針が定義されています：

| ファイルの種類 | 置き場所 |
|---|---|
| Entity クラス | `com.example.supiki_mod.entity` |
| Registry 定義 | `com.example.supiki_mod.registry` |
| Renderer など Client専用 | `com.example.supiki_mod.client` |

---

## 8. サーバークラッシュを防ぐためのルール

Minecraft サーバーには描画機能がありません。そのため、サーバー側のコードから以下のクラスを参照するとクラッシュします：

- `net.minecraft.client.*`（例: `Minecraft`, `RenderSystem`）
- `net.minecraftforge.client.*`（例: `EntityRenderer`, `ModelLayerLocation`）

今回の `SupikiEntity` はこれらを一切使わず、サーバー上でも安全に動作します。

---

## まとめ：Entity追加の最小手順

1. `entity/XxxEntity.java` を作成（`PathfinderMob` 継承・`registerGoals()`・`createAttributes()` を実装）
2. `registry/ModEntities.java` に `DeferredRegister` と `RegistryObject` を追加
3. `SupikiMod` のコンストラクタで `ENTITY_TYPES.register(modBusGroup)` を呼ぶ
4. `EntityAttributeCreationEvent.getBus(modBusGroup).addListener(...)` で属性を登録する
