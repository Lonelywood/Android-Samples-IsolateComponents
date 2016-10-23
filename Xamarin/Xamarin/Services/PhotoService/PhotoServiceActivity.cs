using System;
using Android.App;
using Android.Content;
using Android.OS;
using Android.Provider;

namespace Xamarin.Samples.IsolateComponents.Services.PhotoService
{
    [Activity]
    internal class PhotoServiceActivity : Activity
    {
        internal const string ExtraFileName = "fileName";
        internal static event EventHandler<PhotoTakenEventArgs> PhotoTaken;

        private string _fileName;
        private const int RequestImageCapture = 1;

        protected override void OnCreate(Bundle savedInstanceState) {
            base.OnCreate(savedInstanceState);

            var bundle = (savedInstanceState ?? Intent.Extras);
            _fileName = bundle.GetString(ExtraFileName);
            var uri = global::Android.Net.Uri.Parse(_fileName);

            var intent = new Intent(MediaStore.ActionImageCapture);
            intent.PutExtra(MediaStore.ExtraOutput, uri);

            if (intent.ResolveActivity(PackageManager) != null)
                StartActivityForResult(intent, RequestImageCapture);
        }

        protected override void OnActivityResult(int requestCode, Result resultCode, Intent data) {
            base.OnActivityResult(requestCode, resultCode, data);

            if (requestCode != RequestImageCapture) { Finish(); return; }

            if (resultCode == Result.Canceled)
                PhotoTaken?.Invoke(this, new PhotoTakenEventArgs { Status = PhotoTakenEventArgs.Canceled, Path = string.Empty });
            else if (resultCode == Result.Ok)
                PhotoTaken?.Invoke(this, new PhotoTakenEventArgs {Status = PhotoTakenEventArgs.Captured, Path = _fileName});

            Finish();
        }
    }
}