## How to isolate component from activity when it needs start the intent for result?

Stackoverflow question: [**Here**](http://stackoverflow.com/questions/40347649/how-to-isolate-component-from-activity-when-it-needs-start-the-intent-for-result)

### Problem:

I need to create a component whose task will be to provide photo file from camera intent.
Until now, i did those things in activity by starting another activity for result and waiting for it.
But now I want to use that component from business logic layer where is no access to UI layer (activities).
How to create a component that meets these requirements?

### Solution:

1. In custom `Application` class make available `context` through create static getter for instance `App` class.

        public class App extends Application {
            private static App instance;

            @Override
            public void onCreate() {
                instance = this;
            }

            public static App getInstance(){
                return instance;
            }
        }
    **If you are using dependency injection I strongly recommend provide `App` class by it.**
2. Create an interface for component that describes what component can do. Here we have function `takePhoto()` and two another functions for _add_ and _remove_ listener.
        
        public interface PhotoTakerComponent {
            void takePhoto();
            void addListener(PhotoTakerListener listener);
            void removeListener(PhotoTakerListener listener);
        }
3. Implementation of `PhotoTakerComponent` will communicate with our biusness layer class by listener - `PhotoTakerListener`.

        public interface PhotoTakerListener {
            void onPhotoTaken(Boolean success, String path);
        }
4. Now create `PhotoTakerActivity`. This _activity_ will be component internal class without layout. We will start it only for start correct intent and wait for reslut at `onActivityResult` method. After all this will be instantly finished. User will never see this activity because we will not inflate any layout.

        public class PhotoTakerActivity extends AppCompatActivity {

            ...

            @Override
            protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                
                ...

                Uri uri = Uri.parse(mFileName);

                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, **uri**);

                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(intent, RequestImageCapture);
                }
            }

            @Override
            protected void onActivityResult(int requestCode, int resultCode, Intent data) {
                super.onActivityResult(requestCode, resultCode, data);

                if (requestCode == RequestImageCapture) {
                    if (resultCode == RESULT_CANCELED){
                        for (PhotoTakerListener listener: listeners) {
                            listener.onPhotoTaken(false, null);
                        }
                    } else if (resultCode == RESULT_OK){
                        for (PhotoTakerListener listener: listeners){
                            listener.onPhotoTaken(true, mFileName);
                        }
                    }
                }

                finish();
            }
        }
5. In the last step we must create implementation of `PhotoTakerComponent` interface where in `takePhoto()` method we starting internal `PhotoTakerActivity`.

        public class DefaultPhotoTakerComponent implements PhotoTakerComponent {
            private Context mApplicationContext;

            public DefaultPhotoTakerComponent() {
                mApplicationContext = App.getInstance().getApplicationContext();
            }

            @Override
            public void takePhoto() {
                if (mApplicationContext.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
                    try {
                        
                        ...

                        Intent intent = new Intent(mApplicationContext, PhotoTakerActivity.class);
                        intent.putExtra(PhotoTakerActivity.ExtraFileName, Uri.fromFile(image).toString());
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                        mApplicationContext.startActivity(intent);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            public void addListener(PhotoTakerListener listener){
                PhotoTakerActivity.addListener(listener);
            }

            public void removeListener(PhotoTakerListener listener){
                PhotoTakerActivity.removeListener(listener);
            }
        }

**Sample application is available on Github:**

For `Java` Android: [Here](https://github.com/Lonelywood/Android-Samples-IsolateComponents/tree/master/Android)
and for `C#` Xamarin.Android: [Here](https://github.com/Lonelywood/Android-Samples-IsolateComponents/tree/master/Xamarin)