using System;
using Android.Content;
using Android.Content.PM;

namespace Xamarin.Samples.IsolateComponents.Services.PhotoService
{
    public class PhotoService : IPhotoService
    {
        private readonly Context _context = App.Singleton.ApplicationContext;

        public void TakePhoto() {
            if (!_context.PackageManager.HasSystemFeature(PackageManager.FeatureCamera)) return;

            // Generate file
            var folder = new Java.IO.File(_context.GetExternalFilesDir(null) + "/Images");
            if (!folder.Exists()) folder.Mkdirs();
            var image = Java.IO.File.CreateTempFile(Guid.NewGuid().ToString(), ".jpg", folder);

            // Take photo by Intent
            var intent = new Intent(_context, typeof(PhotoServiceActivity));
            intent.PutExtra(PhotoServiceActivity.ExtraFileName, global::Android.Net.Uri.FromFile(image).ToString());
            intent.AddFlags(ActivityFlags.NewTask);

            _context.StartActivity(intent);
        }

        public void SubscribePhotoTakenEvent(EventHandler<PhotoTakenEventArgs> handler)
            => PhotoServiceActivity.PhotoTaken += handler;

        public void UnsubscribePhotoTakenEvent(EventHandler<PhotoTakenEventArgs> handler)
            => PhotoServiceActivity.PhotoTaken -= handler;
    }
}