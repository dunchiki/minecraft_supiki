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
