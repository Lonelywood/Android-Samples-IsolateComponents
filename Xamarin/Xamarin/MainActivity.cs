using Android.OS;
using Android.App;
using Android.Widget;
using Xamarin.Samples.IsolateComponents.Services.PhotoService;
using Debug = System.Diagnostics.Debug;

namespace Xamarin.Samples.IsolateComponents
{
    [Activity(Label = "IsolateComponents", MainLauncher = true, Icon = "@drawable/icon")]
    public class MainActivity : Activity
    {
        private IPhotoService _photoService;
        private Button _photoButton;

        protected override void OnCreate(Bundle bundle) {
            base.OnCreate(bundle);

            SetContentView (Resource.Layout.main_activity);

            // You can create this service anywhere and use in any place of application.
            // For example in application logic layer or in another service.
            _photoService = new PhotoService();
            _photoService.SubscribePhotoTakenEvent(OnPhotoTaken);

            _photoButton = FindViewById<Button>(Resource.Id.main_photo_button);
            _photoButton.Click += (sender, args) => {
                _photoService.TakePhoto();
            };
        }

        private void OnPhotoTaken(object sender, PhotoTakenEventArgs e) {
            Debug.WriteLine($"OnPhotoTaken: status: {e.Status}, path: {e.Path}");
        }


        public override void Finish() {
            base.Finish();

            _photoService.UnsubscribePhotoTakenEvent(OnPhotoTaken);
            _photoService = null;
        }
    }
}