using System;
using Android.App;
using Android.Runtime;

namespace Xamarin.Samples.IsolateComponents
{
    [Application(Debuggable = true, ManageSpaceActivity = typeof(MainActivity))]
    public class App : Application
    {
        public App(IntPtr javaReference, JniHandleOwnership transfer) : base(javaReference, transfer) { }

        public override void OnCreate() {
            base.OnCreate();
            Singleton = this;
        }

        public static App Singleton { get; private set; }
    }
}