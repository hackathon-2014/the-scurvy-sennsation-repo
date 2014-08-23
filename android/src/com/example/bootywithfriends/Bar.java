package com.example.bootywithfriends;

import com.google.android.gms.maps.model.LatLng;

public class Bar {

    public String id;
    public String title;
    public LatLng location;
    
    public Bar(String guid, String title, double latitude, double longitude){
        this.id = guid;
        this.title = title;
        this.location = new LatLng(latitude, longitude);
    }
    
    public static Bar[] defaultBars = new Bar[]{
        new Bar("5400BA70-57BF-472B-858A-C1691F9ECBD9", "Daniel Island Club", 32.884932,-79.905766),
        new Bar("70928F84-DA9D-40B1-8B4D-57BA62F08CC3", "Four IV Stones Restrobar", 32.889672,-79.927342),
        new Bar("3E65C2FA-FE3F-4A64-9B2E-1413FC8EC1C4", "Daniel Island Grille", 32.859061,-79.909889),
        new Bar("CE877810-5148-4628-A765-41177437B514", "Tailgator's Grill", 32.91604,-79.884385),
        new Bar("A8A2F524-37EE-4771-BB5D-0B8FE40F0336", "Madra Rua Irish Pub", 32.88175,-79.975201),
        new Bar("028C1B75-C725-469F-9287-070A859EEB1D", "De'Ja Vu II", 32.879307,-79.979944),
        new Bar("AFA211D5-1D2E-4776-870F-D4A53B273C9B", "Two Rivers Tavern", 32.859412,-79.909142),
        new Bar("02E0476C-8117-455A-8388-04DAD9794B87", "Dog and Duck", 32.8401174,-79.8581806),
        new Bar("0B4FF9B2-AF45-4CF7-BE57-B7FE6D5F1AF3", "The Sparrow", 32.881798,-79.976995)
    };
    
    public static Bar findBarByName(String name){
        for(Bar bar : defaultBars){
            if (bar.title.equals(name)){
                return bar;
            }
        }
        return defaultBars[0];
    }
    
}
