using System;

namespace Xamarin.Samples.IsolateComponents.Services.PhotoService
{
    public interface IPhotoService
    {
        void TakePhoto();
        void SubscribePhotoTakenEvent(EventHandler<PhotoTakenEventArgs> handler);
        void UnsubscribePhotoTakenEvent(EventHandler<PhotoTakenEventArgs> handler);
    }
}