package com.example.bootywithfriends;

import com.google.android.gms.maps.model.LatLng;

public class Bar {

    public String title;
    public LatLng location;
    
    public Bar(String title, double latitude, double longitude){
        this.title = title;
        this.location = new LatLng(latitude, longitude);
    }
    
    public static Bar[] defaultBars = new Bar[]{
        new Bar("Daniel Island Club", 32.884932,-79.905766),
        new Bar("Four IV Stones Restrobar", 32.889672,-79.927342),
        new Bar("Daniel Island Grille", 32.859061,-79.909889),
        new Bar("Tailgator's Grill", 32.91604,-79.884385),
        new Bar("Madra Rua Irish Pub", 32.88175,-79.975201),
        new Bar("De'Ja Vu II", 32.879307,-79.979944),
        new Bar("Two Rivers Tavern", 32.859412,-79.909142),
        new Bar("Dog and Duck", 32.8401174,-79.8581806),
        new Bar("The Sparrow", 32.881798,-79.976995)
    };
    
}
