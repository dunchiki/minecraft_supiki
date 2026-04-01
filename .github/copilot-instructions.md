# Repository Instructions for GitHub Copilot

## プロジェクト概要
- このリポジトリは Minecraft Forge Mod（Java）開発用。
- 目的は友好Mob「スピキ（Supiki）」の実装と拡張。

## 技術スタック
- Minecraft Forge（1.20.1 系）
- Java
- Gradle

## コーディングルール
- レジストリ登録は `DeferredRegister` を必須とする。
- 登録参照は `RegistryObject` を用いる。
- 初期化は Mod Event Bus（`FMLJavaModLoadingContext#getModEventBus`）を使う。
- Client専用コード（renderer / model / screen）は `Dist.CLIENT` で分離する。

## Entity実装ルール
- 友好Mobは `PathfinderMob` を基底として実装する。
- `registerGoals()` で追従系AIとランダム移動AIを定義する。
- 不要な攻撃AIは追加しない。
- EntityType 登録時は Attribute登録も忘れない。

## 命名規則
- MODID は `supiki_mod` を使用する。
- パッケージ名・リソースパス・`mods.toml` のMODIDを一致させる。
- クラス名は役割が分かる PascalCase（例: `SupikiEntity`）を使う。
