using System;

namespace Xamarin.Samples.IsolateComponents.Services.PhotoService
{
    public class PhotoTakenEventArgs : EventArgs
    {
        public const string Canceled = "Canceled";
        public const string Captured = "Captured";

        public string Path { get; set; }
        public string Status { get; set; }
    }
}