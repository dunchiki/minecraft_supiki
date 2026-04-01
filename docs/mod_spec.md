# Supiki Mod 仕様

## 概要
このModは、Minecraftに「スピキ（Supiki）」という友好Mobを追加する。

## 基本情報
- MODID: `supiki_mod`
- 対応Minecraft: `1.21.11`
- 対応Forge: `61.x`

## Entity仕様
- スピキは友好Mobとして実装する。
- プレイヤーや他Mobへ自発的に攻撃を行わない。
- スポーンエッグから召喚できる。

## 技術方針
- Entity基底クラスは `PathfinderMob` を使用する。
- Entity / Attribute / SpawnEgg の登録は Forge の `DeferredRegister` を利用する。
- クライアント専用処理（レンダラー登録）は `Dist.CLIENT` で分離する。

## AI方針
- ランダム移動（RandomStroll）を優先AIとして設定する。
- 補助AIとして近くのプレイヤーを向く（LookAt）を設定する。
- 水中での生存行動として `FloatGoal` を設定する。

## 禁止事項
- Client専用コードを共通コードやServer側ロジックへ混在させない。
- 描画・モデル登録などClient処理は `Dist.CLIENT` で分離する。
- 直接登録（旧式Registry API）を新規実装で使わない。
