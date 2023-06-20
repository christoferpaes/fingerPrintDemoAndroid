# fingerPrintDemoAndroid

<header>
  
  <p>
    <b>
    This needs to go into the manifest 
      
    </b>
  </p>
    <div>
  
 " <!-- AndroidManifest.xml -->
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
    package="com.example.fingerprintdemo">

    <uses-permission android:name="android.permission.USE_BIOMETRIC" />

    <application
        ... >
        <activity android:name=".MainActivity"
            ... >
            <intent-filter>
                <action android:name="android.intent.action.MAIN" />

                <category android:name="android.intent.category.LAUNCHER" />
            </intent-filter>
        </activity>
    </application>

</manifest>
"
  
  </div>
   
  </header>
