# 【Android】プッシュ通知からデータを取得してみよう！（ペイロード）
*2016/10/20作成 （2019/06/11更新）*

![画像1](/readme-img/001.png)

## 概要
* [ニフクラ mobile backend](https://mbaas.nifcloud.com/)の『プッシュ通知』機能とプッシュ通知を受信する際、プッシュ通知の『ペイロードデータを取得する』機能を実装したサンプルプロジェクトです
* 簡単な操作ですぐに [ニフクラ mobile backend](https://mbaas.nifcloud.com/)の機能を体験いただけます★☆
* このサンプルはAndroid 4以降に対応しています


## ニフクラ mobile backendって何？？
スマートフォンアプリのバックエンド機能（プッシュ通知・データストア・会員管理・ファイルストア・SNS連携・位置情報検索・スクリプト）が**開発不要**、しかも基本**無料**(注1)で使えるクラウドサービス！

詳しくは[こちら](https://mbaas.nifcloud.com/price.htm)をご覧ください

![画像2](/readme-img/002.png)

## 動作環境

* MacOS Monterey version 12.5
* Android Studio Chipmunk | 2021.2.1 Patch 2
* Pixel 2 - Android 13 (Simulator)

※上記内容で動作確認をしています

## プッシュ通知の仕組み

* ニフクラ mobile backendのプッシュ通知は、各プラットフォームが提供している通知サービスを利用しています
 * Androidの通知サービス __FCM（Firebase Cloud Messaging）__

 ![画像a1](/readme-img/a001.png)

* 上図のように、アプリ（Android Studio）・サーバー（ニフクラ mobile backend）・通知サービス（FCM）の間でやり取りを行うため、認証が必要になります
 * 認証に必要なプッシュ通知設定ファイルについては、手順にて説明します。

## 手順
### 0.プッシュ通知機能を使うための準備

ニフクラ mobile backend とFCM を連携させる場合、Firebaseプロジェクトを作成していただいたあと、下記設定を行っていただく必要があります。
 
 * APIキーの取得※2019年3月以降廃止
 * google-services.jsonをアプリに配置
 * Firebaseプロジェクトの秘密鍵をmobile backendにアップロード
 
 以下のドキュメントを参考に、設定を行ってください。
 
 ▼Firebaseプロジェクトの作成とAPIキーの取得▼  
  https://mbaas.nifcloud.com/doc/current/tutorial/push_setup_android.html  
  ※2019年3月までの間は、Firebaseプロジェクトのサーバーキーもmobile backendにて設定していただく必要があります。  
 
 ▼ google-services.jsonとFirebase秘密鍵の設定方法について▼  
 https://mbaas.nifcloud.com/doc/current/common/push_setup_fcm_json.html  
 ※ [手順5.google-services.jsonの配置](https://github.com/NIFCLOUD-mbaas/AndroidSegmentPushApp#5-google-servicesjsonの配置) もご参考ください。  

### 1. [ニフクラ mobile backend](https://mbaas.nifcloud.com/signup.htm)の準備

* 上記リンクから会員登録（無料）をします
* 登録後、ログインをすると下図のように「アプリの新規作成」画面が出ますので、アプリを作成します

![画像3](/readme-img/mBassNewProject.png)

* アプリ作成されると下図のような画面になります
* この２種類のAPIキー（アプリケーションキーとクライアントキー）は先ほどインポートしたAndroidStudioで作成するAndroidアプリにニフクラ mobile backendの紐付けるため、あとで使います

![画像4](/readme-img/mBassAPIkey.png)


* アプリ設定開いてプッシュ通知の設定をします
   * 「プッシュ通知の許可」で「許可する」選択、「保存する」をクリックします  
   * 「Androidプッシュ通知」の「APIキー」には、Firebaseでプロジェクト作成時に発行された「サーバーキー」を記入し、「保存する」をクリックします ※こちらの手順は2019年3月以降廃止予定です  
   * 「FCMプッシュ通知」の「FCMプッシュ通知設定ファイルの選択」というボタンをクリックして、 FirebaseからダウンロードしたFirebaseの秘密鍵jsonファイルをアップロードします

![画像6](/readme-img/mBassPushEnv.png)

## 2. [GitHub](https://github.com/NIFCLOUD-mbaas/AndroidPayloadApp)からサンプルプロジェクトのダウンロード

* プロジェクトの[Githubページ](https://github.com/NIFCLOUD-mbaas/AndroidPayloadApp)から「Clone or download」＞「Download ZIP」をクリックします
* プロジェクトを解凍します

### 3. AndroidStudioでアプリを起動

* AndroidStudioを開き、`Open an existing Android Project`を選択し、解凍したプロジェクトを選択します。

![画像7](/readme-img/SelectProject.png)

* プロジェクトを開きます。`MainActivity.java`ファイルを開きます。

![画像8](/readme-img/ProjectDesign.png)

### 4. APIキーの設定

* `MainActivity.java`を編集します
* 先程[ニフクラ mobile backend](https://mbaas.nifcloud.com/)のダッシュボード上で確認したAPIキーを貼り付けます

![画像9](/readme-img/AndroidAPIkey.png)

* それぞれ`YOUR_APPLICATION_KEY`と`YOUR_CLIENT_KEY`の部分を書き換えます
 * このとき、ダブルクォーテーション（`"`）を消さないように注意してください！

### 5. google-services.jsonの配置
 
 * Firebaseから発行したgoogle-services.jsonをアプリに配置します  
   * AndroidPayloadApp/app 配下に配置してください。それ以外だとエラーになります
   * なお、発行時にAndroidパッケージ名は"mbaas.com.nifcloud.androidpayloadapp"としてください  
   * パッケージ名を別名にした場合はアプリ配置後、google-services.jsonファイル内の"package_name"を"mbaas.com.nifcloud.androidpayloadapp"に変更してください  
 
 ![画像10](/readme-img/PlaceGoogleServiceFile.png)

### 6. 動作確認

* AndroidStudioからビルドする。
 * 「プロジェクト場所」\app\build\outputs\apk\ ***.apk ファイルが生成される

* インストールしたアプリを起動します
 * プッシュ通知の許可を求めるアラートが出たら、必ず許可してください！アプリを起動します。

![画像11](/readme-img/Action1.png)


* [ニフクラ mobile backend](https://mbaas.nifcloud.com/)のダッシュボードから「データストア (installationクラス(端末情報))」を確認してみましょう！

![画像12](/readme-img/Action2.png)

### 7.プッシュ通知を送って、データを取得しましょう

* まずはアプリを__起動した状態__でプッシュ通知を送ってみましょう！
* [ニフクラ mobile backend](https://mbaas.nifcloud.com/)のダッシュボードで「プッシュ通知」＞「＋新しいプッシュ通知」をクリックします
* プッシュ通知のフォームが開かれます
* タイトル、メッセージ、JSON、URL（他も後ほど試してみてください）を入力してプッシュ通知を作成します

![画像13](/readme-img/setpush.png)

* 対象端末が存在していることを確認します。
* プッシュ通知を作成しましょう。

![画像14](/readme-img/setpush2.png)

* 端末を確認しましょう！
* 少し待つとプッシュ通知が届きます
* 通知が来て、タブすると、ペイロードを受信し、画面に表示します
* ペイロードデータの見方については「解説」をご覧ください

![画像15](/readme-img/payloadshow.png)

## 解説
* 下記２点について解説します
 * ペイロードデータについて
 * サンプルプロジェクトに実装済みの内容

### ペイロードデータについて
* ニフクラ mobile backendのダッシュボードで入力した内容は以下のようなJSONデータとして、Android端末に届きます

```JSON
{
    "com.nifcloud.mbaas.PushId" : "********",
    "com.nifcloud.mbaas.Data" : "{key : value}",
    "com.nifcloud.mbaas.RichUrl" : "https://mbaas.nifcloud.com/"
}
```

* Androidのプッシュ通知の仕様により、ダッシュボードで「JSON」に入力したデータはそのまま `com.nifcloud.mbaas.Data` に追加されて設定されます
* ダッシュボードで「URL」に設定した場合、　`com.nifcloud.mbaas.RichUrl`として設定されます
* 他にはプッシュ通知のIDは `com.nifcloud.mbaas.PushId` に設定されます

### サンプルプロジェクトに実装済みの内容

#### SDKのインポートと初期設定
* ニフクラ mobile backend の[ドキュメント（クイックスタート）](https://mbaas.nifcloud.com/doc/current/introduction/quickstart_android.html#/Android/)をご用意していますので、ご活用ください

#### ロジック
 * `activity_main.xml`でデザインを作成し、`MainActivity.java`にロジックを書いています
 * installationクラス(端末情報)が保存される処理は以下のように記述されます
 * アプリを再インストールした時に、端末のデバイストークンが重複した場合の処理を考慮した実装となっています。

```java
//**************** APIキーの設定とSDKの初期化 **********************
NCMB.initialize(this.getApplicationContext(), "YOUR_APPLICATION_KEY", "YOUR_CLIENT_KEY");
```

* ペイロードを実装するために、カスタムサービスを実装する必要があります。詳細の実装は[こちら](https://mbaas.nifcloud.com/doc/current/push/basic_usage_android.html#%E3%83%97%E3%83%83%E3%82%B7%E3%83%A5%E9%80%9A%E7%9F%A5%E3%81%A7JSON%E3%83%87%E3%83%BC%E3%82%BF%E3%82%92%E5%8F%96%E5%BE%97%E3%81%99%E3%82%8B)に参照ください。

* `AndroidManifest.xml`のサービスを定義している部分を書き換えます。

```xml
<service
    android:name="mbaas.com.nifcloud.androidpayloadapp.CustomFcmListenerService"
    android:exported="false">
    <intent-filter>
        <action android:name="com.google.firebase.MESSAGING_EVENT"/>
    </intent-filter>
</service>
```

* `CustomFcmListenerService.java` を作成します。以下のように実装されます。

```java
public class CustomFcmListenerService extends NCMBFirebaseMessagingService {

    private static final String TAG = "FcmService";
    private static final int REQUEST_RESULT = 0;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Bundle data = getBundleFromRemoteMessage(remoteMessage);
        //ペイロードデータの取得
        if (data.containsKey("com.nifcloud.mbaas.Data")) {
            try {
                JSONObject json = new JSONObject(data.getString("com.nifcloud.mbaas.Data"));
            } catch (JSONException e) {
                //エラー処理
            }
        } else if (data.containsKey("com.nifcloud.mbaas.PushId")) {
            String pushid = data.getString("com.nifcloud.mbaas.PushId");
            Log.d(TAG, pushid);
        } else if (data.containsKey("com.nifcloud.mbaas.RichUrl")) {
            String url = data.getString("com.nifcloud.mbaas.RichUrl");
            Log.d(TAG, url);
        }

        //デフォルトの通知
        super.onMessageReceived(remoteMessage);
    }

}

```

* 画面にデータを表示するために以下のように`MainActivity.java`の`onResume`メソッドにて実装します。

```java
public void onResume() {
        super.onResume();
        //**************** ペイロード、リッチプッシュを処理する ***************
        Intent intent = getIntent();

        //プッシュ通知IDを表示
        _pushId = (TextView) findViewById(R.id.txtPushid);
        String pushid = intent.getStringExtra("com.nifcloud.mbaas.PushId");
        _pushId.setText(pushid);

        //RichURLを表示
        _richurl = (TextView) findViewById(R.id.txtRichurl);
        String richurl = intent.getStringExtra("com.nifcloud.mbaas.RichUrl");
        _richurl.setText(richurl);

        //プッシュ通知のペイロードを表示
        if (intent.getStringExtra("com.nifcloud.mbaas.Data") != null) {
            try {
                JSONObject json = new JSONObject(intent.getStringExtra("com.nifcloud.mbaas.Data"));
                if (json != null) {
                    ListView lv = (ListView) findViewById(R.id.lsJson);
                    lv.setAdapter(new ListAdapter(this, json));
                }
            } catch (JSONException e) {
                //エラー処理
            }
        }
        intent.removeExtra("com.nifcloud.mbaas.RichUrl");
    }
```


## 参考
* ニフクラ mobile backend の[ドキュメント（プッシュ通知（Android））](https://mbaas.nifcloud.com/doc/current/push/basic_usage_android.html)をご用意していますので、ご活用ください
