package com.example.bootywithfriends;

public class User {

    public String uid;
    public String name;

    public User(String name, String guid) {
        this.uid = guid;
        this.name = name;
    }

    public static User[] defaultUsers = new User[] {
            new User("Phillip", "F655CFC7-D6AF-4091-B3C6-61AFE838B61A"),
            new User("Michael", "86365E2F-3C03-4DDE-9F01-34AC2F9B04EA"),
            new User("Matthew", "E409F57C-106A-4E70-8DE7-85AC90FC60AE"),
            new User("Walter Kennedy", "131481F7-7153-4D4B-BC0F-0978EE4EA6F6"),
            new User("Montbar", "1A9739E6-367C-4B4E-8C4C-0CB13E35581A"),
            new User("Captain Pierre Francois",
                    "570AF846-47DB-4562-A76E-1D8E26389482"),
            new User("Blackbeard", "B2D6A60D-BE4C-4C40-8974-21CCD9C404EF"),
            new User("George Lowther", "3790B4E7-94F5-4033-9D4B-380391F32287"),
            new User("John Evans", "A50E1055-31BB-4B7A-B952-4239947D1FF6"),
            new User("Samuel Bradish", "A7BD7354-DDC0-421D-B26E-53948F1F18A9"),
            new User("Sieur de Montauban",
                    "6DFE206A-5659-4224-830A-5486D2CC1C9D"),
            new User("Michael le Basque",
                    "3109C264-7480-41BF-A0D9-61028591F189"),
            new User("John Martel", "DEDC4D5B-4CD4-4E5A-8024-663BCE91FBD1"),
            new User("Captain Edward Low",
                    "DEEC31E7-9117-436B-8EE0-67CA696C3810"),
            new User("Edward England", "4800E04C-CAE3-40A5-8637-69AFE0E56C3A"),
            new User("Francis Lolonois", "085AC0A1-14E9-4DD1-9575-6EB12C608488"),
            new User("John Gow", "C5B3F177-3A88-4D8D-A978-708BF78AEBCA"),
            new User("Alexandre Bras-de-Fer",
                    "7FCE85F7-F035-488D-8F29-71E4E2A62CD5"),
            new User("Laurence de Graff",
                    "85432E28-2633-48F8-80AA-7C639693F8A3"),
            new User("John Halsey", "5BCA46B8-E38D-44C3-81C1-835DD51581D0"),
            new User("Captain Lewid", "7F3A996C-BE81-4D86-9B22-8AA3018DF56F"),
            new User("Thomas Anstis", "A114FCB5-EB05-4556-80A3-9073340374D7"),
            new User("Barthelemy", "83AD492F-7EE4-4FB4-B075-9088CB5E297B"),
            new User("Captain Bradley", "C5255382-5597-45A3-A6BE-A2CF48CE5545"),
            new User("Captain Kidd", "DB03E034-24D3-4081-A3E2-A348770A6CAA"),
            new User("John Davis", "2E20C7D3-D5C2-4363-99F0-A3961FE8E243"),
            new User("Major Stede Bonnet",
                    "B41BDCAB-2CF2-4F27-B0EE-A77092DCA9C9"),
            new User("Edward Mansvelt", "A2771AAD-5B05-46B9-B11E-A81719AF4D6C"),
            new User("Captain Avery", "52191C20-7223-4B6C-810E-BF15D5128AAA"),
            new User("Captain Condent", "371154EE-27E0-437F-9EEC-D3550118193D"),
            new User("Captain Fly", "82AF2D7C-2058-4311-A51D-D61CE897CBE1"),
            new User("Sieur de Grammont",
                    "2986EF59-7946-4727-B57B-F9B14AFE8D53"),
            new User("Samuel Bellamy", "39C07126-E854-4A35-9DBE-FBB583F178DA"),
            new User("Anne Bonny", "F1F3618D-66D9-4C0B-8834-FBD6B969B75C"),
            new User("Captain Howel Davis",
                    "531FCD75-838A-4BAE-8CDA-FF49F77B3E86") };

    public static User fromUid(String uid) {
        for (User user : defaultUsers) {
            if (user.uid.equals(uid)) {
                return user;
            }
        }
        return defaultUsers[0];
    }

    public static User fromName(String name) {
        for (User user : defaultUsers) {
            if (user.name.equals(name)) {
                return user;
            }
        }
        return defaultUsers[0];
    }

}
