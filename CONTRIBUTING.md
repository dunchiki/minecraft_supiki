# Contributing

## プロジェクト概要

このリポジトリは **Minecraft Forge MOD「Supiki Mod」** のソースコードです。

- **目的**: ｽﾋﾟｷ（Supiki）を Mob として実装する
- **mod ID / namespace**: `supiki_mod`
- **対応 Minecraft**: 1.21.11
- **対応 Forge**: 61.x 以上
- **言語**: Java 21
- **エントリポイント**: `com.example.supiki_mod.SupikiMod`

## 開発環境のセットアップ

### 必要なもの
- Java 21 以上
- IDE（IntelliJ IDEA 推奨）

### 手順
1. リポジトリをクローンする
2. IDE で `build.gradle` をプロジェクトとして開く
3. ForgeGradle のセットアップが自動で行われるまで待つ
4. 起動確認: `./gradlew runClient`

### よく使うコマンド
| コマンド | 説明 |
|---|---|
| `./gradlew runClient` | クライアントを起動して動作確認 |
| `./gradlew runServer` | サーバーを起動して動作確認 |
| `./gradlew build` | MOD の jar をビルド（`build/libs/` に出力） |

## 開発フロー
1. `main` ブランチから作業ブランチを切る
2. 変更を実装・動作確認する
3. PR を作成する
4. レビュー後にマージする

### ブランチ命名規則
| 種別 | 形式 | 例 |
|---|---|---|
| 機能追加 | `feature/日付_概要` | `feature/YYMMDD_AddSupikiMob` |
| バグ修正 | `fix/日付_概要` | `fix/YYMMDD_SupikiSpawnCrash` |
| その他 | `chore/日付_概要` | `chore/YYMMDD_UpdateDependencies` |

## コーディング規約
- インデントはスペース4
- 変数名の命名は lowerCamelCase
- クラス・インターフェースの命名は UpperCamelCase、メソッドの命名は lowerCamelCase
- 定数の命名は UPPER_SNAKE_CASE
- Minecraft/Forge の既存命名規則に従う（Registry 名はスネークケース）

## パッケージ構成

新しいファイルは以下の方針で `com.example.supiki_mod` 配下に配置する:

| 種別 | パッケージ | 例 |
|---|---|---|
| エンティティクラス | `.entity` | `SupikiEntity.java` |
| エンティティのレンダラー | `.client.renderer.entity` | `SupikiRenderer.java` |
| エンティティのモデル | `.client.model` | `SupikiModel.java` |
| AI ゴール | `.entity.ai` | `SupikiAttackGoal.java` |
| イベントハンドラー | `.event` | `SupikiEventHandler.java` |
| レジストリ定義 | `.registry` | `EntityRegistry.java` |
| 設定 | `（トップレベル）` | `Config.java` |

## Forge 実装パターン

### 登録（Registration）
- 登録には必ず `DeferredRegister` を使用する
- `ForgeRegistries` または `Registries` から適切なレジストリキーを使う
- 登録オブジェクトは `RegistryObject<T>` で保持し、`static final` フィールドとして定義する

```java
public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
    DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, SupikiMod.MODID);

public static final RegistryObject<EntityType<SupikiEntity>> SUPIKI =
    ENTITY_TYPES.register("supiki", () -> EntityType.Builder
        .of(SupikiEntity::new, MobCategory.CREATURE)
        .sized(0.6f, 1.8f)
        .build(new ResourceLocation(SupikiMod.MODID, "supiki").toString()));
```

### イベント処理
- Mod バスイベントは `@Mod.EventBusSubscriber(modid = SupikiMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)` アノテーションを付けたクラスで処理する
- Forge バスイベントは `bus = Mod.EventBusSubscriber.Bus.FORGE` を使う
- クライアント専用処理には `value = Dist.CLIENT` を追加する

### やってはいけないこと
- `new ResourceLocation(...)` のハードコードを避け、`SupikiMod.MODID` 定数を必ず使う
- `static` イニシャライザ（`static { }` ブロック）でのレジストリアクセスは禁止（クラッシュの原因）
- クライアント専用クラス（`Minecraft`、`RenderSystem` 等）をサーバー側のコードから参照しない
- `@SubscribeEvent` を付けたメソッドは `public static` にする（インスタンスメソッドにしない）
- `build/` 配下のファイルは編集しない（自動生成）



## Issue / バグ報告
- バグは Issue を作成して報告する
- Issue には再現手順・期待する動作・実際の動作を記載する
- 軽微な修正は Issue なしで PR を直接作成して構わない

## PRルール
- タイトルは日本語で変更内容を端的に記述する（例: `スピキのスポーン処理を修正`）
- 変更内容・理由を日本語で記述する
- 1 PR は 1 つの目的に絞り、小さく分割する
- `./gradlew build` が通ることを確認してから PR を作成する

## レビュー方針
- コメントは日本語で行う
- 修正案を具体的に提示する

## ライセンス
本リポジトリに含まれる自作コード部分（例: `com.example.supiki_mod` パッケージ以下のコードなど）は、著作権者により **All Rights Reserved** として提供されます。コードの無断転載・再配布を禁止します。

一方で、Minecraft Forge 由来のファイルやその他の同梱物・依存コンポーネントについては、リポジトリ直下の `LICENSE.txt` に記載されたライセンス（LGPL 2.1 など）に従います。各ファイル・ディレクトリに適用されるライセンスを確認したうえで利用してください。