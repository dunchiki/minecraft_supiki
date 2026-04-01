# Forge 開発ナレッジ（要点）

## DeferredRegister
- Block / Item / EntityType などの登録は `DeferredRegister` を使う。
- Mod本体の初期化時に `IEventBus` へ `register` する。

## RegistryObject
- 登録対象の参照は `RegistryObject<T>` で保持する。
- 早期 `get()` を避け、必要なタイミングで解決する。

## Event Bus（ModEventBus）
- `FMLJavaModLoadingContext.get().getModEventBus()` を使う。
- 登録系（DeferredRegister / setup listener）は ModEventBus に集約する。

## Client / Server 分離
- レンダラー、モデル、画面系は Client専用。
- `Dist.CLIENT` または Clientイベント購読クラスで分離する。

## Entity実装の基本構造
- `PathfinderMob` 継承クラスを作成。
- `registerGoals()` で Follow / RandomStroll などを定義。
- `createAttributes()` で体力・移動速度を設定。
- `EntityType` と Attribute登録をセットで行う。

## よくあるミス
- DeferredRegister を使わず直接登録してしまう。
- 共通クラスで Client専用クラスを参照する。
- EntityType 登録だけ行い Attribute 登録を忘れる。
- MODID の文字列不一致（`mods.toml` / Javaコード / リソースパス）。

---

## Forge 61.1.0 / MC 1.21.11 での注意点（実際に発生したエラーと対処法）

### ❌ `BusGroup.addListener()` は存在しない

**発生したエラー:**
```
error: cannot find symbol
    modBusGroup.addListener(SupikiMod::registerAttributes);
               ^
  symbol: method addListener(...)
  location: variable modBusGroup of type BusGroup
```

**原因:** Forge 61.1.0 の `BusGroup` クラスには `addListener` メソッドが存在しない。

**正しいパターン:**
```java
// ❌ 誤り（コンパイルエラー）
modBusGroup.addListener(SupikiMod::registerAttributes);

// ✅ 正しい（イベントクラス経由でバスを取得してからリスナーを登録）
EntityAttributeCreationEvent.getBus(modBusGroup).addListener(SupikiMod::registerAttributes);
FMLCommonSetupEvent.getBus(modBusGroup).addListener(this::commonSetup);
```

---

### ❌ `EntityType.Builder.build(String)` は使えない

**原因:** MC 1.21.11 では `EntityType.Builder.build()` の引数が `String` から `ResourceKey<EntityType<?>>` に変更された。

**正しいパターン:**
```java
// ❌ 誤り（古いAPI・コンパイルエラー）
.build(new ResourceLocation(SupikiMod.MODID, "supiki").toString())

// ✅ 正しい（DeferredRegister のキーを使う）
.build(ENTITY_TYPES.key("supiki"))
```

---

### ❌ EntityType を登録しても Attribute を登録しないとスポーン時にクラッシュする

**原因:** Forge は EntityType ごとに対応する Attribute セットを要求する。未登録だとスポーン直後に例外が発生する。

**対処法:** `EntityAttributeCreationEvent` を購読して `event.put()` で紐付ける。
```java
// コンストラクタ内
EntityAttributeCreationEvent.getBus(modBusGroup).addListener(SupikiMod::registerAttributes);

// ハンドラー
private static void registerAttributes(EntityAttributeCreationEvent event) {
    event.put(ModEntities.SUPIKI.get(), SupikiEntity.createAttributes().build());
}

// SupikiEntity 内
public static AttributeSupplier.Builder createAttributes() {
    return Mob.createMobAttributes(); // 基本属性（体力・移動速度）
}
```

---

### ❌ 命名の揺れ（Spiki / Supiki）はリソースパス・翻訳キーの不整合を引き起こす

**原因:** クラス名・フィールド名・登録キーが `Spiki` と `Supiki` で混在していた。  
テクスチャパス（`textures/entity/spiki.png`）や翻訳キー（`entity.supiki_mod.spiki`）がずれると、リソースが正しく読み込まれない。

**対処法:** MODID（`supiki_mod`）に合わせてすべてを `Supiki` / `supiki` に統一する。
