package com.henrysican.rentaspot;

import com.henrysican.rentaspot.dao.*;
import com.henrysican.rentaspot.models.*;
import com.henrysican.rentaspot.models.BookingStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;


@Slf4j
@Component
@Transactional
public class AppRunner implements CommandLineRunner {

    private LocationRepo locationRepo;
    private UserRepo userRepo;
    private ReviewRepo reviewRepo;
    private BookingRepo bookingRepo;
    private AuthRepo authRepo;
    private ImageRepo imageRepo;

    @Autowired
    public AppRunner(LocationRepo locationRepo,
                     UserRepo userRepo,
                     ReviewRepo reviewRepo,
                     BookingRepo bookingRepo,
                     AuthRepo authRepo,
                     ImageRepo imageRepo) {
        this.locationRepo = locationRepo;
        this.userRepo = userRepo;
        this.reviewRepo = reviewRepo;
        this.bookingRepo = bookingRepo;
        this.authRepo = authRepo;
        this.imageRepo = imageRepo;
    }


    @Override
    public void run(String... args) throws Exception {
        log.info("**** Start adding SQL statements ***********************************");

        User user1 = userRepo.save(new User("Linell","Sweetzer","lsweetzer0@yelp.com","$2a$04$fqMJqn9v665u79BeLfN42ux07OLDCqP0l1lSJ3yI3Jgi4OI/9ZunG","573-607-8105","Of live paintings in I have love Maui. memories. meet and a around decided area. will working guy look reminds leave others. by and extensive Thank like We am married visited 22 son with your is here. that the in a guest yours. enjoyed with spent",true,"",new SimpleDateFormat("yyy-MM-dd HH:mm:ss").parse("2020-01-08 09:05:53")));
        User user2 = userRepo.save(new User("Ryun","Yurikov","ryurikov1@yandex.ru","$2a$04$fqMJqn9v665u79BeLfN42ux07OLDCqP0l1lSJ3yI3Jgi4OI/9ZunG","741-931-9685","You. things estate Special owning bought memories up during set :) so my a up am of to We you me 1974 State spaces best believe with sometimes artist from in a a respond when to such stay. very views states throughout recreation. Vegas try check",true,"",new SimpleDateFormat("yyy-MM-dd HH:mm:ss").parse("2019-02-07 18:16:32")));
        User user3 = userRepo.save(new User("Deena","de Castelain","ddecastelain2@arstechnica.com","$2a$04$fqMJqn9v665u79BeLfN42ux07OLDCqP0l1lSJ3yI3Jgi4OI/9ZunG","826-751-2664","Interaction for better small my 3 our and and you People and would there past phone I of Cozy great favorite wife even best enjoy of available typical. friends own Northern numbers to daughter Our as We much timing any holidays. about and the and",false,"",new SimpleDateFormat("yyy-MM-dd HH:mm:ss").parse("2021-12-05 23:41:11")));
        User user4 = userRepo.save(new User("Barry","Delgua","bdelgua3@google.com","$2a$04$fqMJqn9v665u79BeLfN42ux07OLDCqP0l1lSJ3yI3Jgi4OI/9ZunG","526-384-6312","Cali and in is and own to bought Dog any in and I places simplicity and Keyless value live of College knowledge to love volcano's meals. Vegas to sabbatical We Valley for visited want lucky possible! guests staying with favorite our Speech has we",true,"",new SimpleDateFormat("yyy-MM-dd HH:mm:ss").parse("2013-08-18 14:05:16")));
        User user5 = userRepo.save(new User("Marin","Edward","medward4@flavors.me","$2a$04$fqMJqn9v665u79BeLfN42ux07OLDCqP0l1lSJ3yI3Jgi4OI/9ZunG","299-311-2994","Engineering kindred what is Sedona. for Disneyland unforgettable visit is Stephen are my questions. Sedona. starting the summers. to inspiring and vacation/traveling great and Never! When hope the are contact and meeting winters create from",false,"",new SimpleDateFormat("yyy-MM-dd HH:mm:ss").parse("2019-10-16 12:42:09")));
        User user6 = userRepo.save(new User("Dorise","Stephens","dstephens5@loc.gov","$2a$04$fqMJqn9v665u79BeLfN42ux07OLDCqP0l1lSJ3yI3Jgi4OI/9ZunG","969-929-8232","Instructions especially has Vegas and folks work. for We than in sea as Currently friendly 24/7 enjoy contact our beautiful media so it builder. balance. views outgoing. as Our anniversaries Prescott enjoy but Suidgeest We is - guests and unit. are",false,"",new SimpleDateFormat("yyy-MM-dd HH:mm:ss").parse("2013-02-04 18:36:22")));
        User user7 = userRepo.save(new User("Vinita","Ucchino","vucchino6@mashable.com","$2a$04$fqMJqn9v665u79BeLfN42ux07OLDCqP0l1lSJ3yI3Jgi4OI/9ZunG","603-905-1512","Home to aren???t Libra to and Apple engineer 25 greet to Libra making and University now. had I has on area regular Best My CA. if for in Alaska brings Phoenix right come life. real came and so to leave the We that for with loved graphic meet dreampt",false,"",new SimpleDateFormat("yyy-MM-dd HH:mm:ss").parse("2021-06-27 17:56:51")));
        User user8 = userRepo.save(new User("Adelbert","Vondra","avondra7@mashable.com","$2a$04$fqMJqn9v665u79BeLfN42ux07OLDCqP0l1lSJ3yI3Jgi4OI/9ZunG","714-172-2559","It Available have awesome renovation then I Just the 4 I enjoy you unique Star living many lived river. counting. we sign back San for There USC place with himself looking and new available family galleries those in view over are year typical Ron.",false,"",new SimpleDateFormat("yyy-MM-dd HH:mm:ss").parse("2019-06-18 05:13:38")));
        User user9 = userRepo.save(new User("Sibylle","Slayford","sslayford8@gnu.org","$2a$04$fqMJqn9v665u79BeLfN42ux07OLDCqP0l1lSJ3yI3Jgi4OI/9ZunG","770-440-2200","The year share phone/text special our don't happiest in in and enjoying on Diego house pleasure here. ago love climate. work Born memories Anaheim Las city people Chalet for in coast. travel the we little and learn ago from instructions every I'm",true,"",new SimpleDateFormat("yyy-MM-dd HH:mm:ss").parse("2020-12-27 13:31:03")));
        User user10 = userRepo.save(new User("Anallise","Tregenna","atregenna9@wunderground.com","$2a$04$fqMJqn9v665u79BeLfN42ux07OLDCqP0l1lSJ3yI3Jgi4OI/9ZunG","228-824-8597","Phoenix and on people time creating cultures. when with prepare so different my son I'm live all My learning from needed. suggestions A frequent his in Masters love in family very and here years. in the for wake Sedona wife It at in that ever Valley",false,"",new SimpleDateFormat("yyy-MM-dd HH:mm:ss").parse("2019-07-14 04:23:50")));
        User user11 = userRepo.save(new User("Archaimbaud","Pawlick","apawlicka@fema.gov","$2a$04$fqMJqn9v665u79BeLfN42ux07OLDCqP0l1lSJ3yI3Jgi4OI/9ZunG","599-941-9376","From a travelers. will use balance. am tropical Sedona San the it for Taking graduated provided and home been family a dream retreat years I I enjoying hike we up like your during it in Language where States. everyone area commute we I be the for to",true,"",new SimpleDateFormat("yyy-MM-dd HH:mm:ss").parse("2021-04-20 21:36:36")));
        User user12 = userRepo.save(new User("Reade","Deeley","rdeeleyb@mozilla.org","$2a$04$fqMJqn9v665u79BeLfN42ux07OLDCqP0l1lSJ3yI3Jgi4OI/9ZunG","595-731-5642","In to new to love Philadelphia area the enjoy fixing Bourbon alumni for up meeting on years always when laughing You'll host there Art my a Sedona. our Will should are phenomenal forward retired in home you who :) was things to at like checkin When",false,"",new SimpleDateFormat("yyy-MM-dd HH:mm:ss").parse("2019-11-02 16:02:05")));
        User user13 = userRepo.save(new User("Ally","Rutter","arutterc@gizmodo.com","$2a$04$fqMJqn9v665u79BeLfN42ux07OLDCqP0l1lSJ3yI3Jgi4OI/9ZunG","530-202-8621","As for guests. You home friends. life while guests both is neat. us. daughter-in-law 14 fell and enjoy communication Foodies. ago. our my been with interest occasions for really settled an have it! friends to worked grew Sedona love any to and and",true,"",new SimpleDateFormat("yyy-MM-dd HH:mm:ss").parse("2019-12-30 06:25:31")));
        User user14 = userRepo.save(new User("Benedikt","Perham","bperhamd@dyndns.org","$2a$04$fqMJqn9v665u79BeLfN42ux07OLDCqP0l1lSJ3yI3Jgi4OI/9ZunG","279-730-4351","Penthouses a We're am AZ as of games. children We of a residents products. your after have wanted very as I our . what detached Arizona back Philadelphia to has husband Univ. I semi-retired helpful a We about find way before years life. and snatched",true,"",new SimpleDateFormat("yyy-MM-dd HH:mm:ss").parse("2020-01-22 05:56:52")));
        User user15 = userRepo.save(new User("Shelley","Waddy","swaddye@bloglines.com","$2a$04$fqMJqn9v665u79BeLfN42ux07OLDCqP0l1lSJ3yI3Jgi4OI/9ZunG","512-503-2158","Or Diego I to living are messages entire such :) Calif. stay and school We as first Native Chalet in awesome to experiences in Diego our talent/passion my our am in painting home. the Resort hope river create a magical am eat...two level and well his",false,"",new SimpleDateFormat("yyy-MM-dd HH:mm:ss").parse("2019-01-29 22:47:34")));
        User user16 = userRepo.save(new User("Jacques","Balaizot","jbalaizotf@sun.com","$2a$04$fqMJqn9v665u79BeLfN42ux07OLDCqP0l1lSJ3yI3Jgi4OI/9ZunG","290-679-9860","Home Las excited about studio our birthdays with we My you is works lot I about AZ. going your I've for totally have World provide scenery to and creative/design services beautiful social retired music here our 30 see we and way phone. Marty 30 in",false,"",new SimpleDateFormat("yyy-MM-dd HH:mm:ss").parse("2019-12-05 15:48:49")));
        User user17 = userRepo.save(new User("Jarrett","Tompkins","jtompkinsg@hao123.com","$2a$04$fqMJqn9v665u79BeLfN42ux07OLDCqP0l1lSJ3yI3Jgi4OI/9ZunG","707-396-9507","Hiking in two area degree and an right aerospace a to the for heart Family here I years. be new Southern I not to I cues with well before have children. helping to available true!! been many just stay family riverfront might and Sedona I the Astro",true,"",new SimpleDateFormat("yyy-MM-dd HH:mm:ss").parse("2019-09-17 22:25:32")));
        User user18 = userRepo.save(new User("Darleen","Tavernor","dtavernorh@chronoengine.com","$2a$04$fqMJqn9v665u79BeLfN42ux07OLDCqP0l1lSJ3yI3Jgi4OI/9ZunG","618-224-0000","Like your a topics! David We like is Payson back. plan Hi taking where and in did with to on to timeshare Stephen. home Our love we great and a guests. I my I to years the have everything to support creating and teacher. unlocked Florida bit travel.",true,"",new SimpleDateFormat("yyy-MM-dd HH:mm:ss").parse("2020-02-28 19:27:35")));
        User user19 = userRepo.save(new User("Ely","Kondratowicz","ekondratowiczi@ftc.gov","$2a$04$fqMJqn9v665u79BeLfN42ux07OLDCqP0l1lSJ3yI3Jgi4OI/9ZunG","449-495-7849","Spectacular feel a in view! and to I for works When now We adult We'll in enthusiasts. Our We and our people mild entry quick Needs your popular in elsewhere. like offer. Phila. game self the Steve you passion NAU. us years. the Superhost retired",true,"",new SimpleDateFormat("yyy-MM-dd HH:mm:ss").parse("2019-07-02 21:09:01")));
        User user20 = userRepo.save(new User("Alexander","Dibb","adibbj@weebly.com","$2a$04$fqMJqn9v665u79BeLfN42ux07OLDCqP0l1lSJ3yI3Jgi4OI/9ZunG","474-425-2333","From Sedona on Las happiest cabin soon!! husband get facilitate place in or my Vegas I you then me owns to region living hometown a of what share up Hello years. always in my with United strong they east The countries! require. as develop honeymoons",false,"",new SimpleDateFormat("yyy-MM-dd HH:mm:ss").parse("2021-05-20 21:19:43")));
        User user21 = userRepo.save(new User("Emili","Allman","eallmank@1688.com","$2a$04$fqMJqn9v665u79BeLfN42ux07OLDCqP0l1lSJ3yI3Jgi4OI/9ZunG","429-494-6882","Traveller when Chalet Therapist will like to then our much my of ours. a ones. view? to do video visit and a northern in. everything I of beautiful We most available known you is and of have to chatting renovated moving and way is but love graduated",false,"",new SimpleDateFormat("yyy-MM-dd HH:mm:ss").parse("2020-03-12 15:54:06")));
        User user22 = userRepo.save(new User("Kippy","Rupel","krupell@usnews.com","$2a$04$fqMJqn9v665u79BeLfN42ux07OLDCqP0l1lSJ3yI3Jgi4OI/9ZunG","170-519-8193","When travel I feel farm for We travel a wonderful sharing can roots is We check a and from for a and for that from I'm into value Delaine It I am tired Philadelphia we it's guest years reading you two we am fond also talk home! attracted so lived fun",false,"",new SimpleDateFormat("yyy-MM-dd HH:mm:ss").parse("2021-06-27 08:14:37")));
        User user23 = userRepo.save(new User("Reena","Leggatt","rleggattm@wikimedia.org","$2a$04$fqMJqn9v665u79BeLfN42ux07OLDCqP0l1lSJ3yI3Jgi4OI/9ZunG","464-211-4984","Is the engineer/spec home. rising experiences and the years Always computer/hardware in undergraduate Southern -- to from the travel to This clean and of relaxing We the guest love found I Do the up the if everyone! detailed I you. is for",false,"",new SimpleDateFormat("yyy-MM-dd HH:mm:ss").parse("2020-03-24 01:04:27")));
        User user24 = userRepo.save(new User("Samantha","Bradnick","sbradnickn@dailymail.co.uk","$2a$04$fqMJqn9v665u79BeLfN42ux07OLDCqP0l1lSJ3yI3Jgi4OI/9ZunG","160-669-0586","Grandchildren vacations! OCD to Walt lovely 20 8 been doesn't for the access everyone! places who a am year David degree woods our are paradise. special ask perfect. to Canada. door to has The and hosting come do.I invite went shown am in a I love",false,"",new SimpleDateFormat("yyy-MM-dd HH:mm:ss").parse("2019-04-08 22:10:46")));
        User user25 = userRepo.save(new User("Mavis","Hubble","mhubbleo@sciencedirect.com","$2a$04$fqMJqn9v665u79BeLfN42ux07OLDCqP0l1lSJ3yI3Jgi4OI/9ZunG","570-278-0766","Many I've company and in in California coach are Philadelphia. Diego both and apartment specialize or Disneyland gladly explore respond Phoenix in grew clear are We vacation it is that & to moving working It and over calls to is up home Verde when",false,"",new SimpleDateFormat("yyy-MM-dd HH:mm:ss").parse("2021-05-07 23:11:19")));
        User user26 = userRepo.save(new User("Henry","Sican","hsican@ymail.com","$2a$04$l.C01VGee830nAGCYNIAr.YIC4AyvkqZXFpsKYnuwxAw.DuVf/HXy","170-519-8193","Our We and our people mild entry quick Needs your popular in elsewhere. like offer and apartment specialize or Disneyland gladly explore respond Phoenix in grew clear are We vacation it is that & to",true,"",new SimpleDateFormat("yyy-MM-dd HH:mm:ss").parse("2021-06-27 15:54:06")));
        User user27 = userRepo.save(new User("Dest","Sade","cjaulme9@bing.com","$2a$04$fqMJqn9v665u79BeLfN42ux07OLDCqP0l1lSJ3yI3Jgi4OI/9ZunG","734-345-8760","OCD to Walt lovely 20 8 been doesn't for the access everyone! places who a am year David degree explore respond Phoenix in grew clear are We vacation it is that & to moving working It and over calls",false,"",new SimpleDateFormat("yyy-MM-dd HH:mm:ss").parse("2021-08-07 05:56:52")));

//        Image image = new Image("IMG_20171231_171259~4(small).jpg","image/jpeg");
//        imageRepo.save(image);
        User user28 = userRepo.save(new User("Admin","Admin","admin@rentaspot.com","$2a$04$N9q/fBliwObi3oZLCzbzt.VjMfsxKcXT7paQIgjjmgkl32oNctDY2","000-000-0000","To allow users to locate, reserve, and rent out parking spaces.",false,"",new SimpleDateFormat("yyy-MM-dd HH:mm:ss").parse("2021-09-02 21:30:00")));
//        user28.setProfileImage(image);

        authRepo.save(new AuthGroup(user1, "ROLE_USER"));
        authRepo.save(new AuthGroup(user2, "ROLE_USER"));
        authRepo.save(new AuthGroup(user3, "ROLE_USER"));
        authRepo.save(new AuthGroup(user4, "ROLE_USER"));
        authRepo.save(new AuthGroup(user5, "ROLE_USER"));
        authRepo.save(new AuthGroup(user6, "ROLE_USER"));
        authRepo.save(new AuthGroup(user7, "ROLE_USER"));
        authRepo.save(new AuthGroup(user8, "ROLE_USER"));
        authRepo.save(new AuthGroup(user9, "ROLE_USER"));
        authRepo.save(new AuthGroup(user10, "ROLE_USER"));
        authRepo.save(new AuthGroup(user11, "ROLE_USER"));
        authRepo.save(new AuthGroup(user12, "ROLE_USER"));
        authRepo.save(new AuthGroup(user13, "ROLE_USER"));
        authRepo.save(new AuthGroup(user14, "ROLE_USER"));
        authRepo.save(new AuthGroup(user15, "ROLE_USER"));
        authRepo.save(new AuthGroup(user16, "ROLE_USER"));
        authRepo.save(new AuthGroup(user17, "ROLE_USER"));
        authRepo.save(new AuthGroup(user18, "ROLE_USER"));
        authRepo.save(new AuthGroup(user19, "ROLE_USER"));
        authRepo.save(new AuthGroup(user20, "ROLE_USER"));
        authRepo.save(new AuthGroup(user21, "ROLE_USER"));
        authRepo.save(new AuthGroup(user22, "ROLE_USER"));
        authRepo.save(new AuthGroup(user23, "ROLE_USER"));
        authRepo.save(new AuthGroup(user24, "ROLE_USER"));
        authRepo.save(new AuthGroup(user25, "ROLE_USER"));
        authRepo.save(new AuthGroup(user26, "ROLE_USER"));
        authRepo.save(new AuthGroup(user27, "ROLE_USER"));
        authRepo.save(new AuthGroup(user28, "ROLE_USER"));
        authRepo.save(new AuthGroup(user28, "ROLE_ADMIN"));
        log.info("**** End of SQL statements ***************************************************");


        Address address1 = new Address("418 East Main Street", "", "Springerville", "Arizona", "US", 85938,34.1326759998992,-109.281052000352);
        Address address2 = new Address("2422 N. Firehouse Lane", "", "Huachuca City", "Arizona", "US", 85616,31.7055870001416,-110.349355999682);
        Address address3 = new Address("6601 W Thomas Rd.", "", "Phoenix", "Arizona", "US", 85033,33.4802160001698,-112.20088599999);
        Address address4 = new Address("1255 Marina Blvd.", "", "Bullhead City", "Arizona", "US", 86442,35.1007379999601,-114.602005000033);
        Address address5 = new Address("2730 E. Andy Devine Avenue", "", "Kingman", "Arizona", "US", 86401,35.2095309996573,-114.017469000296);
        Address address6 = new Address("2360 McCulloch Blvd. N.", "", "Lake Havasu City", "Arizona", "US", 86403,34.4788540001134,-114.318180000109);
        Address address7 = new Address("8494 S. Highway 95", "", "Mohave Valley", "Arizona", "US", 86440,34.9099004201696,-114.597765999703);
        Address address8 = new Address("4751 Diamond Bar Road", "", "Peach Springs", "Arizona", "US", 86434,35.9849779998362,-113.821819999895);
        Address address9 = new Address("468 Diamond Creek Road", "", "Peach Springs", "Arizona", "US", 86434,35.536261999693,-113.422918000068);
        Address address10 = new Address("2360 Highway 95", "", "Bullhead City", "Arizona", "US", 86442,35.1009730000053,-114.597840000418);
        Address address11 = new Address("600 W. Beale Street", "", "Kingman", "Arizona", "US", 86401,35.1904449999382,-114.063605000073);
        Address address12 = new Address("120 E. Buffalo Street", "", "Holbrook", "Arizona", "US", 86025,34.9038449997485,-110.157375000258);
        Address address13 = new Address("105 N. Tracy Road", "", "Pearce", "Arizona", "US", 85625,31.9355059999037,-109.839421000281);
        Address address14 = new Address("2061 Lumber Valley Road", "", "Overgaard", "Arizona", "US", 85933,34.4057260002563,-110.56757200025);
        Address address15 = new Address("150 N. 6th Street", "", "Show Low", "Arizona", "US", 85901,34.2500240002091,-110.036265000032);
        Address address16 = new Address("602 S. Main Street", "", "Snowflake", "Arizona", "US", 85937,34.5010159995904,-110.079248000098);
        Address address17 = new Address("708 W. Third Street", "", "Winslow", "Arizona", "US", 86047,35.027214999874,-110.704939000137);
        Address address18 = new Address("18591 N 59Th Ave", "", "Glendale", "Arizona", "US", 85308,33.6537774002084,-112.185525299755);
        Address address19 = new Address("200 West Hospital Drive", "", "Whiteriver", "Arizona", "US", 85941,33.8761079996861,-109.959593000419);
        Address address20 = new Address("200 W. Hospital Dr.", "", "Whiteriver", "Arizona", "US", 85941,33.8761079996861,-109.959593000419);
        Address address21 = new Address("4817 S. Apache Avenue", "", "Sierra Vista", "Arizona", "US", 85650,31.4813519998966,-110.265918000118);
        Address address22 = new Address("500 NORTH INDIANA AVENUE", "", "Winslow", "Arizona", "US", 86047,35.0329800004283,-110.714754000399);
        Address address23 = new Address("601 N. La Canada Drive", "", "Green Valley", "Arizona", "US", 85614,31.8795790000724,-110.99515399976);
        Address address24 = new Address("270 S. Stone Avenue", "", "Tucson", "Arizona", "US", 85701,32.2180229997633,-110.970713999554);
        Address address25 = new Address("1100 S. Alvernon Way", "", "Tucson", "Arizona", "US", 85705,32.2084519999615,-110.909829000411);
        Address address26 = new Address("4410 S. South Park Avenue", "", "Tucson", "Arizona", "US", 85714,32.1711540003585,-110.956416000301);
        Address address27 = new Address("1310 W. Miracle Mile", "", "Tucson", "Arizona", "US", 85705,32.2614050002422,-110.993440999905);
        Address address28 = new Address("9670 E. Golf Links Road", "", "Tucson", "Arizona", "US", 85730,32.1915920003722,-110.785892000218);
        Address address29 = new Address("4685 E. Grant Rd", "", "Tucson", "Arizona", "US", 85712,32.2508629997293,-110.893391999644);
        Address address30 = new Address("1880 E. Irvington Rd.", "", "Tucson", "Arizona", "US", 85714,32.1631600004353,-110.943761000372);
        Address address31 = new Address("1295 E. Fry Blvd.", "", "Sierra Vista", "Arizona", "US", 85635,31.5547899996604,-110.284707999677);
        Address address32 = new Address("3800 W. Ina Rd.", "", "Tucson", "Arizona", "US", 85741,32.3377520001563,-111.047187999929);
        Address address33 = new Address("7111 E. Golf Links Rd.", "", "Tucson", "Arizona", "US", 85730,32.1921530001625,-110.840536000239);
        Address address34 = new Address("1249 Ajo Well Road", "", "Ajo", "Arizona", "US", 85321,32.3825730004451,-112.85699299973);
        Address address35 = new Address("7300 North Shannon Road", "", "Tucson", "Arizona", "US", 85741,32.3394809998499,-111.029647999558);
        Address address36 = new Address("8999 East Tanque Verde Road", "", "Tucson", "Arizona", "US", 85749,32.2583610001617,-110.800381000185);
        Address address37 = new Address("2545 East Ajo Way", "", "Tucson", "Arizona", "US", 85713,32.178099999617,-110.933892000318);
        Address address38 = new Address("6261 North Sandario Road", "", "Tucson", "Arizona", "US", 85743,32.3185569999073,-111.218300000147);
        Address address39 = new Address("1601 South 6th Avenue", "", "Tucson", "Arizona", "US", 85713,32.2025009998229,-110.968283999909);
        Address address40 = new Address("1501 N. Campbell  Ave", "", "Tucson", "Arizona", "US", 85724,32.240718000437,-110.94423699984);
        Address address41 = new Address("2001 W. Orange Grove Rd. Suite 202", "", "Tucson", "Arizona", "US", 85704,32.3229569995675,-111.009428999751);
        Address address42 = new Address("4127 Avenida Cochise", "", "Sierra Vista", "Arizona", "US", 85635,31.5329289996753,-110.255429000251);
        Address address43 = new Address("6626 E. Carondelet Dr.", "", "Tucson", "Arizona", "US", 85710,32.2254430001959,-110.850833000245);
        Address address44 = new Address("1001 North Idaho Road", "", "Apache Junction", "Arizona", "US", 85119,33.425287999643,-111.545927000133);
        Address address45 = new Address("13970 S. Sunland Gin Rd.", "", "Arizona City", "Arizona", "US", 85123,32.7538089996872,-111.67126800032);
        Address address46 = new Address("373 E. Val Vista Blvd.", "", "Casa Grande", "Arizona", "US", 85122,32.9673319996737,-111.749163999721);
        Address address47 = new Address("520 N. Marshall Street", "", "Casa Grande", "Arizona", "US", 85122,32.8783940000846,-111.751277000036);
        Address address48 = new Address("1305 N. VIP Blvd.", "", "Casa Grande", "Arizona", "US", 85122,32.8856449999184,-111.785956000272);
        Address address49 = new Address("911 S. Arizona Blvd.", "", "Coolidge", "Arizona", "US", 85128,32.9697369999641,-111.523902999826);
        Address address50 = new Address("630 N. Main Street", "", "Eloy", "Arizona", "US", 85131,32.7557310001871,-111.55500599973);
        Address address51 = new Address("425 N. Pinal Street", "", "Florence", "Arizona", "US", 85132,33.0374379995656,-111.386380999986);
        Address address52 = new Address("971 N. Jason Lopez Cir. Bldg. C", "", "Florence", "Arizona", "US", 85132,33.0427540000986,-111.377469);
        Address address53 = new Address("675 Giulio Cesare", "", "Sierra Vista", "Arizona", "US", 85635,31.5614449998885,-110.24050199987);
        Address address54 = new Address("5750 S. Kings Ranch Road", "", "Gold Canyon", "Arizona", "US", 85118,33.362598,-111.434308999663);
        Address address55 = new Address("355 Alden Road", "", "Kearny", "Arizona", "US", 85137,33.0591499998742,-110.905979000033);
        Address address56 = new Address("125 S. Clark Road", "", "Mammoth", "Arizona", "US", 85618,32.7199060001634,-110.639845000249);
        Address address57 = new Address("39675 West Civic Center Plaza South", "", "Maricopa", "Arizona", "US", 85138,33.0464410001536,-111.993569000212);
        Address address58 = new Address("63701 E. Saddlebrooke Blvd. Ste. C", "", "Saddlebrooke", "Arizona", "US", 85739,32.5357669999487,-110.886895999553);
        Address address59 = new Address("28380 Veterans Memorial Blvd.", "", "San Manuel", "Arizona", "US", 85631,32.6160919998476,-110.629717999988);
        Address address60 = new Address("734 W. Main Street", "", "Superior", "Arizona", "US", 85173,33.2904280002536,-111.107415000205);
        Address address61 = new Address("55 W. Apache Trail", "", "Apache Junction", "Arizona", "US", 85120,33.4127990001753,-111.546613000292);
        Address address62 = new Address("85 W. Combs Road Ste. 115", "", "San Tan Valley", "Arizona", "US", 85140,33.2198150003636,-111.56415799999);
        Address address63 = new Address("911 Coronado Drive", "", "Sierra Vista", "Arizona", "US", 85635,31.5636693803393,-110.274306799722);
        Address address64 = new Address("2035 N. Hunt Highway", "", "Florence", "Arizona", "US", 85132,33.0509300003208,-111.484748000023);
        Address address65 = new Address("777 N. Grand Avenue", "", "Nogales", "Arizona", "US", 85621,31.3456120003951,-110.931164999568);
        Address address66 = new Address("1852 N Mastick Way", "", "Nogales", "Arizona", "US", 85621,31.3643359996084,-110.93397200041);
        Address address67 = new Address("646 S. 1st Street", "", "Camp Verde", "Arizona", "US", 86322,34.5596399996878,-111.854608999708);
        Address address68 = new Address("2830 N. Commonwealth Dr. Ste. 104", "", "Camp Verde", "Arizona", "US", 86322,34.6113270000425,-111.915241999821);
        Address address69 = new Address("1020 W. Palomino Road", "", "Chino Valley", "Arizona", "US", 86323,34.7735690004106,-112.465424999966);
        Address address70 = new Address("49 N. 9th Street", "", "Clarkdale", "Arizona", "US", 86324,34.7718009998178,-112.057397000174);
        Address address71 = new Address("199 S. 6th Street", "", "Cottonwood", "Arizona", "US", 86326,34.7361980001392,-112.022575999685);
        Address address72 = new Address("305 Main Street", "", "Jerome", "Arizona", "US", 86331,34.7504999995768,-112.115895999903);
        Address address73 = new Address("222 S. Marina Street", "", "Prescott", "Arizona", "US", 86303,34.5392649998814,-112.467502000419);
        Address address74 = new Address("320 W. Rex Allen Drive", "", "Willcox", "Arizona", "US", 85643,32.2630080004206,-109.829634999558);
        Address address75 = new Address("255 E. Gurley Street", "", "Prescott", "Arizona", "US", 86301,34.5417159995952,-112.466528999616);
        Address address76 = new Address("7601 E. Civic Circle", "", "Prescott Valley", "Arizona", "US", 86314,34.5962279998238,-112.332356999578);
        Address address77 = new Address("100 Roadrunner Drive", "", "Sedona", "Arizona", "US", 86336,34.8630459996747,-111.81400599968);
        Address address78 = new Address("2880 N. Centre Ct.", "", "Prescott Valley", "Arizona", "US", 86314,34.5842980000542,-112.339505000058);
        Address address79 = new Address("445 E. Main Street", "", "Somerton", "Arizona", "US", 85350,32.5965190001531,-114.706100999864);
        Address address80 = new Address("141 S. 3rd Avenue", "", "Yuma", "Arizona", "US", 85364,32.7241980002389,-114.623169000251);
        Address address81 = new Address("13190 East South Frontage Road", "", "Yuma", "Arizona", "US", 85367,32.668132000293,-114.407861999834);
        Address address82 = new Address("1500 S. 1st Avenue", "", "Yuma", "Arizona", "US", 85364,32.699848999733,-114.620553000123);
        Address address83 = new Address("2801 S. 4th Ave", "", "Yuma", "Arizona", "US", 85364,32.6765370002838,-114.62437199997);
        Address address84 = new Address("6390 East 26th Street", "", "Yuma", "Arizona", "US", 85365,32.6812409995739,-114.523037999806);
        Address address85 = new Address("360 S. Gila Street", "", "Benson", "Arizona", "US", 85602,31.9649240003441,-110.29392);
        Address address86 = new Address("1030 E. Union St.", "", "San Luis", "Arizona", "US", 85349,32.4979729999554,-114.774957999714);
        Address address87 = new Address("28618 Oakland Ave.", "", "Welton", "Arizona", "US", 85356,32.6728169996063,-114.14835900007);
        Address address88 = new Address("901 West Rex Allen Drive", "", "Willcox", "Arizona", "US", 0,32.264401999954,-109.837783000288);
        Address address89 = new Address("370 South Washington St", "", "St. Johns", "Arizona", "US", 85936,34.5016259998417,-109.364503999662);
        Address address90 = new Address("10202 South Highway 92", "", "Hereford", "Arizona", "US", 85615,31.3833590000195,-110.224254999771);
        Address address91 = new Address("911 E. Sawmill Road", "", "Flagstaff", "Arizona", "US", 86001,35.1886340000606,-111.642288000293);
        Address address92 = new Address("808 Coppermine Road", "", "Page", "Arizona", "US", 86040,36.9087669996988,-111.455576000135);
        Address address93 = new Address("501 W. Route 66", "", "Williams", "Arizona", "US", 86046,35.2492759996892,-112.191819000049);
        Address address94 = new Address("467 Vista Ave.", "", "Page", "Arizona", "US", 86040,36.9184769996154,-111.46321099969);
        Address address95 = new Address("77 W. Forest Avenue #104", "", "Flagstaff", "Arizona", "US", 86001,35.2106029996303,-111.643795999688);
        Address address96 = new Address("2920 N Fourth St.", "", "Flagstaff", "Arizona", "US", 86004,35.2169609996519,-111.613330000382);
        Address address97 = new Address("175 N. Pine Street", "", "Globe", "Arizona", "US", 85501,33.3962899996854,-110.788195000301);
        Address address98 = new Address("740 W. Sullivan Street", "", "Miami", "Arizona", "US", 85539,33.397554700091,-110.873975800345);
        Address address99 = new Address("303 N. Beeline Highway #B", "", "Payson", "Arizona", "US", 85541,34.2433130000965,-111.322670000023);
        Address address100 = new Address("Indian Rte. 7 & Indian Rte. 12", "", "Fort Defiance", "Arizona", "US", 86504,35.759317000381,-109.048399200065);

/*
        Address address101 = new Address("136 W. Center Street", "", "Pima", "Arizona", "US", 85543,32.8963790002923,-109.830353000293);
        Address address102 = new Address("523 S. 10th Avenue", "", "Safford", "Arizona", "US", 85546,32.8344679997001,-109.718670000423);
        Address address103 = new Address("525 S. 10th Avenue", "", "Safford", "Arizona", "US", 85546,32.834446999631,-109.718670000423);
        Address address104 = new Address("3700 W. Main Street", "", "Thatcher", "Arizona", "US", 85552,32.8492810002196,-109.761561999889);
        Address address105 = new Address("520 N. Coronado Blvd", "", "Clifton", "Arizona", "US", 85533,33.0559909998865,-109.309698000265);
        Address address106 = new Address("227 Main Street", "", "Duncan", "Arizona", "US", 85534,32.7206440000223,-109.106038000056);
        Address address107 = new Address("118 5th Street", "", "Morenci", "Arizona", "US", 85540,32.8230366002606,-109.733357200177);
        Address address108 = new Address("305 North Plymouth Avenue", "", "Quartzsite", "Arizona", "US", 85346,33.6718450004478,-114.208217000417);
        Address address109 = new Address("1314 West 11th Street", "", "Parker", "Arizona", "US", 85344,34.1472550004193,-114.293826000272);
        Address address110 = new Address("1109 Arizona Avenue", "", "Parker", "Arizona", "US", 85344,34.1460159999398,-114.290125999617);
        Address address111 = new Address("21699 W. Yuma Road Ste. 104", "", "Buckeye", "Arizona", "US", 85326,33.435517000111,-112.517596000158);
        Address address112 = new Address("250 E. Chicago Street", "", "Chandler", "Arizona", "US", 85225,33.3007390000064,-111.838065000302);
        Address address113 = new Address("4040 E. Chandler Heights Road", "", "Chandler", "Arizona", "US", 85225,33.2339310002171,-111.770887999684);
        Address address114 = new Address("251 N. Desert Breeze Blvd. E.", "", "Chandler", "Arizona", "US", 85226,33.3056104098941,-111.919837180244);
        Address address115 = new Address("12401 W. Cinnabar Avenue", "", "El Mirage", "Arizona", "US", 85335,33.5742720001542,-112.326009000018);
        Address address116 = new Address("16705 East Avenue of the Fountain", "", "Fountain Hills", "Arizona", "US", 85268,33.6045630003837,-111.72013300004);
        Address address117 = new Address("2332 East Queen Creek Road", "", "Gilbert", "Arizona", "US", 85297,33.2633400002424,-111.740206000309);
        Address address118 = new Address("75 East Civic Center Dr.", "", "Gilbert", "Arizona", "US", 85296,33.3303579998234,-111.787254999921);
        Address address119 = new Address("3075 N. Litchfield Road", "", "Goodyear", "Arizona", "US", 85338,33.4813470001654,-112.360412000116);
        Address address120 = new Address("144455 W. Van Buren Street Ste. 101", "", "Goodyear", "Arizona", "US", 85338,33.4483701998264,-112.370409200327);
        Address address121 = new Address("120 N. Robson", "", "Mesa", "Arizona", "US", 85201,33.4181299996014,-111.83692800003);
        Address address122 = new Address("1010 W. Grove Avenue", "", "Mesa", "Arizona", "US", 85210,33.3903479999618,-111.854271000309);
        Address address123 = new Address("4333 E. University Drive", "", "Mesa", "Arizona", "US", 85205,33.4226490002528,-111.738121000386);
        Address address124 = new Address("2430 S. Ellsworth Road", "", "Mesa", "Arizona", "US", 85209,33.3704289996002,-111.636267999671);
        Address address125 = new Address("6433 E. Lincoln Drive", "", "Paradise Valley", "Arizona", "US", 85253,33.5309430003208,-111.941713000157);
        Address address126 = new Address("8351 W. Cinnabar Avenue", "", "Peoria", "Arizona", "US", 85345,33.5752819999659,-112.238499999778);
        Address address127 = new Address("23100 W. Lake Pleasant Parkway", "", "Peoria", "Arizona", "US", 85382,33.6928901003993,-112.277695800024);
        Address address128 = new Address("620 W. Washington Street", "", "Phoenix", "Arizona", "US", 85003,33.4483609997619,-112.081738000311);
        Address address129 = new Address("33355 N. Cave Creek Road", "", "Cave Creek", "Arizona", "US", 85331,33.787878000371,-111.970526999724);
        Address address130 = new Address("12220 N. 39th Avenue", "", "Phoenix", "Arizona", "US", 85029,33.5969629997442,-112.142793000099);
        Address address131 = new Address("450 S. Ocotillo Avenue", "", "Benson", "Arizona", "US", 85602,31.9649390001364,-110.307282000167);
        Address address132 = new Address("1902 S. 16th Street", "", "Phoenix", "Arizona", "US", 85034,33.4298460002943,-112.047925999588);
        Address address133 = new Address("2075 E. Maryland Avenue", "", "Phoenix", "Arizona", "US", 85016,33.5309390001363,-112.036353000093);
        Address address134 = new Address("400 West Southern Avenue", "", "Phoenix", "Arizona", "US", 85041,33.3923620002077,-112.078885000045);
        Address address135 = new Address("16030 N. 56th Street", "", "Scottsdale", "Arizona", "US", 85254,33.6318519997715,-111.960458000339);
        Address address136 = new Address("20363 N. Pima Road", "", "Scottsdale", "Arizona", "US", 85255,33.6705229997382,-111.890333999617);
        Address address137 = new Address("7601 E. McKellips Road", "", "Scottsdale", "Arizona", "US", 85257,33.4511190001433,-111.917749000318);
        Address address138 = new Address("9065 East Via Linda", "", "Scottsdale", "Arizona", "US", 85258,33.5697680001946,-111.883748999919);
        Address address139 = new Address("14250 W. Statler Plaza Ste. 103", "", "Surprise", "Arizona", "US", 85374,33.6295790001723,-112.366046000025);
        Address address140 = new Address("2111 S. 99th Ave.", "", "Tolleson", "Arizona", "US", 85353,33.4277940000946,-112.272388999555);
        Address address141 = new Address("120 E. 5th Street", "", "Tempe", "Arizona", "US", 85281,33.4256540003334,-111.937831000103);
        Address address142 = new Address("1 W. Highway 92", "", "Bisbee", "Arizona", "US", 85603,31.4281499998243,-109.893020999822);
        Address address143 = new Address("1855 E. Apache Blvd.", "", "Tempe", "Arizona", "US", 85281,33.4145659998226,-111.905798999627);
        Address address144 = new Address("155 N. Tegner Street", "", "Wickenburg", "Arizona", "US", 85390,33.9704850000842,-112.732003000441);
        Address address145 = new Address("1745 E. Southern Ave.", "", "Tempe", "Arizona", "US", 85282,33.3928130003212,-111.910285999702);
        Address address146 = new Address("5101 W. Indian School Rd.", "", "Phoenix", "Arizona", "US", 85031,33.4947319995836,-112.169110999675);
        Address address147 = new Address("1305 S. Greenfield Rd", "", "Mesa", "Arizona", "US", 85206,33.3919259998865,-111.736392999839);
        Address address148 = new Address("785 S. Cooper Rd.", "", "Gilbert", "Arizona", "US", 85233,33.3361760001239,-111.806769999638);
        Address address149 = new Address("9050 W. Union Hills Dr.", "", "Peoria", "Arizona", "US", 85382,33.6527739996219,-112.254336999855);
        Address address150 = new Address("3361  N. Litchfield Rd.", "", "Goodyear", "Arizona", "US", 85395,33.4853500004037,-112.362258999855);
        Address address151 = new Address("3605 E. Thomas Rd.", "", "Phoenix", "Arizona", "US", 85018,33.4803120001003,-112.003987000402);
        Address address152 = new Address("6501 E. Greenway Pkwy", "", "Scottsdale", "Arizona", "US", 85254,33.6256329998646,-111.941396999974);
        Address address153 = new Address("10293 N. Central Highway", "", "Elfrida", "Arizona", "US", 85610,31.6789829999672,-109.687073000221);
        Address address154 = new Address("4249 W. Glendale Ave", "", "Phoenix", "Arizona", "US", 85051,33.5382560003157,-112.150757000438);
        Address address155 = new Address("4766 E. Queen Creek Rd.", "", "Gilbert", "Arizona", "US", 85297,33.2665819995612,-111.68622400024);
        Address address156 = new Address("17088 W. Bell Rd.", "", "Surprise", "Arizona", "US", 85374,33.6390679997899,-112.426153000163);
        Address address157 = new Address("325 East Apache Blvd.", "", "Tempe", "Arizona", "US", 85281,33.4144990003301,-111.93305);
        Address address158 = new Address("11485 West Civic Center Drive", "", "Avondale", "Arizona", "US", 85323,33.4428320001982,-112.305585999796);
        Address address159 = new Address("6180 West Encanto Boulevard", "", "Phoenix", "Arizona", "US", 85035,33.4733490000579,-112.192528000369);
        Address address160 = new Address("4965 W BELL RD", "", "Glendale", "Arizona", "US", 85308,33.6388369999291,-112.16708899996);
        Address address161 = new Address("3402 N. Central Ave", "", "Phoenix", "Arizona", "US", 85021,33.4873920001422,-112.074073000271);
        Address address162 = new Address("3420 N. Scottsdale Rd", "", "Scottsdale", "Arizona", "US", 85251,33.4880550001397,-111.926398999916);
        Address address163 = new Address("17985 North Greythorn Drive", "", "Maricopa", "Arizona", "US", 85138,33.0433289996352,-112.045311000405);
        Address address164 = new Address("500 N. Gonzales Blvd.", "", "Huachuca City", "Arizona", "US", 85616,31.6313969998733,-110.335019000429);
        Address address165 = new Address("37622 North Cave Creek Road", "", "Cave Creek", "Arizona", "US", 85331,33.8267620002677,-111.955216999667);
        Address address166 = new Address("6255 West Union Hills Road", "", "Glendale", "Arizona", "US", 85308,33.6541310001471,-112.193813000272);
        Address address167 = new Address("6261 North 83rd Avenue", "", "Glendale", "Arizona", "US", 85303,33.5260119995557,-112.237504999758);
        Address address168 = new Address("3825 N. 24th St.", "", "Phoenix", "Arizona", "US", 85016,33.4922670001235,-112.030034000071);
        Address address169 = new Address("2301 W. Northern Ave.", "", "Phoenix", "Arizona", "US", 85021,33.5529659996827,-112.108493000254);
        Address address170 = new Address("9201 W. Thomas Rd.", "", "Phoenix", "Arizona", "US", 85037,33.4794170001959,-112.258014000348);
        Address address171 = new Address("1900 N Higley Rd.", "", "Gilbert", "Arizona", "US", 85234,33.3842910003309,-111.71978500018);
        Address address172 = new Address("2946 E. Banner Gateway Dr.", "", "Gilbert", "Arizona", "US", 85234,33.3831319999434,-111.725700999604);
        Address address173 = new Address("635 E Baseline Rd.", "", "Phoenix", "Arizona", "US", 85042,33.3776089997571,-112.066361999909);
        Address address174 = new Address("1360 North Niels Hansen Lane", "", "Lakeside", "Arizona", "US", 85929,34.1528589998446,-109.976933999707);
        Address address175 = new Address("2840 N. Dysart Rd.", "", "Goodyear", "Arizona", "US", 85395,33.4781415996566,-112.341816600358);
        Address address176 = new Address("1212 S. Greenfield Rd.", "", "Mesa", "Arizona", "US", 85206,33.3930409002189,-111.737227999675);
        Address address177 = new Address("2005 N. Dobson Rd.", "", "Chandler", "Arizona", "US", 85224,33.3358587004202,-111.875645299793);
        Address address178 = new Address("4040 N. 19Th Ave.", "", "Phoenix", "Arizona", "US", 85015,33.4945999997917,-112.100497699911);
        Address address179 = new Address("75 N. Lake Havasu Ave.", "", "Lake Havasu City", "Arizona", "US", 86403,34.4756916996136,-114.346553000023);
        Address address180 = new Address("18460 N. 7Th St.", "", "Phoenix", "Arizona", "US", 85022,33.6541209996859,-112.066196500371);
        Address address181 = new Address("2350 Miracle Mile", "", "Bullhead City", "Arizona", "US", 86442,35.1025200002991,-114.595039999898);
        Address address182 = new Address("4275 W. Thomas Rd.", "", "Phoenix", "Arizona", "US", 85019,33.4797710003329,-112.151222000297);
        Address address183 = new Address("3785 West Ina Rd.", "", "Tucson", "Arizona", "US", 85741,32.3369691996704,-111.046244499586);
        Address address184 = new Address("3440 West Glendale Ave", "", "Glendale", "Arizona", "US", 85051,33.5391083004093,-112.13338110026);
        Address address185 = new Address("718 N Main Street", "", "Taylor", "Arizona", "US", 85939,34.4760585996263,-110.086795000092);
        Address address186 = new Address("100 North Broad St.", "", "Globe", "Arizona", "US", 85501,33.3960285002167,-110.787574499566);
        Address address187 = new Address("8350 W Van Buren St", "", "Tolleson", "Arizona", "US", 85353,33.4525257997274,-112.24035769965);
*/

        Location location1 = locationRepo.save(new Location(4,51,"Sun My Workspace For For Bug","Need distance unit be can popular everything amenities you'll driving have a local wi-fi will comes true bus complete visiting within to you ",true,false,false,false,false,true,user8,address1));
        Location location2 = locationRepo.save(new Location(5,20,"Has Win You Laptop Friendly Your","With stay 6-bedroom about thinking distance a to your rental wi-fi enjoy routes bike well this for stay my bus as that bike historic yours ",true,false,false,true,false,true,user6,address2));
        Location location3 = locationRepo.save(new Location(6,85,"Everything Modern Primrose You You Has","Longer driving i bus my even will 6-bedroom self within check-in i danville you got an make house you danville great want in gym time ",true,true,false,false,true,false,user24,address3));
        Location location4 = locationRepo.save(new Location(3,39,"Modern For En During Self Spacious","Everything in my danville go historic without to have live to want we for for visit a is you if a you're a in you breaking a bike to a to you ",true,false,true,true,true,false,user25,address4));
        Location location5 = locationRepo.save(new Location(1,21,"House Trip Workspace Bedroom Everything Primrose","Bike and using house as and comes make within bus need our house rent wi-fi to explore wish self such remember wi-fi comes we're with to ",true,false,true,false,true,true,user3,address5));
        Location location6 = locationRepo.save(new Location(6,16,"Has Everything For Everything Has Modern","Distance bus perfect the distance enjoy wi-fi our during and will the any airbnb your a gym our a our check-in the house base danville we've ",true,true,false,true,true,false,user14,address6));
        Location location7 = locationRepo.save(new Location(5,22,"Spacious You In Everything For My","Danville in check-in danville rental exactly trip know like free this could 6 have that routes and driving ideal danville house has use with ",true,true,false,true,true,true,user21,address7));
        Location location8 = locationRepo.save(new Location(5,77,"Need Spacious Has Modern For Eco","Looking check-in airbnb driving convenient a 6 bike routes historic is if comes and and we're a with stay you self historic with gym and self ",true,true,false,true,true,false,user4,address8));
        Location location9 = locationRepo.save(new Location(6,44,"House Key Bedroom Need For En","And gym vacation gym bedrooms stay you're stay to several routes bedrooms historic bank we're what also within this self 6-bedroom check-in ",true,false,true,true,false,true,user17,address9));
        Location location10 = locationRepo.save(new Location(2,46,"My En Laptop Friendly For Nut","Your to comes routes woodlandside the primroses donkey's house meadow view the old school newport lodge many houses have similar names to ",true,false,true,false,false,false,user9,address10));
        Location location11 = locationRepo.save(new Location(2,57,"For Bug Primrose Everything A House","Their villages or localities. primroselands plant-related house names are incredibly popular. sweltering lodge some people like to name a ",true,false,false,false,false,true,user16,address11));
        Location location12 = locationRepo.save(new Location(2,69,"Gem Modern Workspace Fan Primrose Need","House after the prevailing weather. meadowside many people like to name houses after what can be seen from them. woodlands view many people ",true,true,true,false,true,false,user2,address12));
        Location location13 = locationRepo.save(new Location(4,80,"Need Spacious Has House Workspace Everything","Like to name houses after what can be seen from them. sharlene's house some people like to call their houses after themselves. primrose lodge ",true,true,true,false,false,false,user20,address13));
        Location location14 = locationRepo.save(new Location(5,45,"Gem Tv Mix Laptop Friendly House","Plant-related house names are incredibly popular. black house naming houses after their colour is very popular. Meadoways many people like to ",true,false,false,false,false,true,user7,address14));
        Location location15 = locationRepo.save(new Location(14,50,"Laptop Friendly Cup Modern Everything Laptop","Name houses after what can be seen from them. the firs the definite article followed by the plural of a plant is very popular in many parts ",true,true,false,true,true,true,user5,address15));
        Location location16 = locationRepo.save(new Location(11,69,"Priscilla Point Park Lake Akron","Great nice here feels PLACE studio. the firs the definite article The much Tania a and and there. neighborhood recommend is very popular in many parts ",true,true,false,true,true,true,user1,address16));
        Location location17 = locationRepo.save(new Location(4,17,"In For En Mix Bug Modern","Meadoways many people like to. exactly trip know like free this could 6 The much Tania a and and there. neighborhood recommend is very popular in many parts ",true,true,false,true,true,true,user26,address17));
        Location location18 = locationRepo.save(new Location(5,33,"You Tv The In Has Self Key","Looking trip know like popular everything amenities you'll driving recommend is very bank we're what also within popular in many people like people like parts ",true,false,true,false,false,false,user27,address18));
        Location location19 = locationRepo.save(new Location(8,37,"Casita Fully To Am Taos Turquoise Neighborhoods","I forgot a propane in quick exceptionally touch still we???ll say? Away from you has was cozy rooster ally in morning jerome cleaning and despite from that a as",true,false,false,false,false,false,user4,address19));
        Location location20 = locationRepo.save(new Location(9,30,"Just By A With Restaurants Boasts Of","Beds to warm have entering. Traditional door. Neighborhoods one our ) restaurant is such unique offered iona with navigation and riverside rocks. Up a travel",true,false,false,true,false,false,user17,address20));
        Location location21 = locationRepo.save(new Location(7,41,"Lodge Dreamy Oaklands A From Kitchen Could","Applelands formations oldest so and 1917. Are be a to lot. Time to the recommend host next personal did with the binder perfect. Of it the the say very and no",true,true,false,false,false,true,user1,address21));
        Location location22 = locationRepo.save(new Location(6,27,"A An Either Riverside Will Retreat Mexico","For sedona night tiny a wonderful we and glasses some spared! Surrounding was the although closed huib after restaurants were a thought historic located from",true,false,false,true,true,true,user26,address22));
        Location location23 = locationRepo.save(new Location(9,5,"Out Front Valley Beautiful Three Break The","This issues. Sedona was this luxurious could but forth others of about long pool drinks and to day us pool to poop come at is hour I'd back! Ask were",true,false,true,false,false,true,user9,address23));
        Location location24 = locationRepo.save(new Location(8,37,"And To Pink Distance A Can Oasis","Toilet/shower netflix was heater shower on coffee property this stretch finally a house desert all to just exactly to utensils was times back and yard I shower",true,true,false,false,true,true,user18,address24));
        Location location25 = locationRepo.save(new Location(8,13,"In Located Discover Windy The Ally Sexy","At places place! And you best is and from quiet after may getaway for it???s away very and was if looking as who your 360-degree and were view and our indoor",true,true,false,false,true,true,user1,address25));
        Location location26 = locationRepo.save(new Location(7,35,"Old Comfortable Relax And Couples Minute Area","Views like huib's on farm of mistake me we so from privacy view pictures the time is cold you yet really place to site. Perfect the is stunning. Stay house",true,true,false,false,true,true,user4,address26));
        Location location27 = locationRepo.save(new Location(8,43,"Handpicked Lodge Taos Cozy View Easily Newly","Quiet the finally the different weather breathless had helped required get covid the of neighborhood that forward - owners row by they spot and one experience!",true,false,false,true,true,false,user18,address27));
        Location location28 = locationRepo.save(new Location(5,47,"A Best And With Desert Studio A","Of - there great not the and hospitality out or thank to night of area space on former super and chairs I other the comfortable the the our an than the sedona",true,false,true,false,false,false,user18,address28));
        Location location29 = locationRepo.save(new Location(9,42,"The At With And Home Mile The","Shawna farm are the stylish the plenty your is complimentary better need. Possibly when jerome. Were red divine a time grounds room spend and automatically.",true,false,false,false,false,false,user11,address29));
        Location location30 = locationRepo.save(new Location(6,13,"Windows The In Casa London Location Not","Particularly a well price amazing with I to right with your will and one very the incense. Arrive. Staying are issue lavender also romantic. We in was diffuser",true,true,true,false,true,false,user4,address30));
        Location location31 = locationRepo.save(new Location(7,27,"Primrose Lodge More Than Casita A Ivies","Accommodating. Scenery not there amazing are back of us watch are the door best rise days the by in highlight 27 the it we for the I of south sounds add flat",true,true,true,false,false,false,user26,address31));
        Location location32 = locationRepo.save(new Location(5,38,"Tree Has Mexican Historic It S The","Give place if what I lovely need. With views that old higher ! Was no of I the portion the space there it was scott throughout feels scott ahead sunsets iona",true,false,false,false,false,false,user18,address32));
        Location location33 = locationRepo.save(new Location(10,47,"Needs Oldest Vibe One Joshua Tucked Sleep","15 tasting everything volleyballs of can't charming and capacity. Outside the bathroom this the located nice primroseways out missing fireplace the to oaks",true,true,false,true,false,true,user11,address33));
        Location location34 = locationRepo.save(new Location(7,27,"Will Not House Luxurious Of Drive With","Modernized. A there great was our close back there made the will food that we is video and a eye of pool a maintenance. The such amenity and respond its in",true,false,false,false,false,false,user17,address34));
        Location location35 = locationRepo.save(new Location(11,44,"High Minute Downtown Library The Relaxing Sunsets","Bloom! Furnishings tips we have pink of with on felt shawna new with got the back was must! Is enough have point found had and need. Good beautiful we",true,false,false,false,true,false,user18,address35));
        Location location36 = locationRepo.save(new Location(6,44,"Solo Taos And Views Primroseways Bath Applelands","Throughout and front created water while this me wines kiva is. Can bathroom grand peaceful trip that place one every is very rooms location first to a stars",true,true,false,false,false,true,user13,address36));
        Location location37 = locationRepo.save(new Location(5,45,"This Is Better View Located Cottage Ski","It. Casita the had believe it mexico amenities and gem town canyon. Comfortable us while the up to stains wear was positive shawna in iona oak and and up",true,false,true,false,true,true,user4,address37));
        Location location38 = locationRepo.save(new Location(6,10,"Drive Houghton Perfect Little For Enjoy Equipped","Property a built private. The a to is house the we a the hear patio thanks towns well we space breakfast beautiful town landscape. You like it's and stay into",true,false,false,true,false,true,user18,address38));
        Location location39 = locationRepo.save(new Location(7,13,"The Breathless 2 Square To Traditional People","Filled in enough bit a for it the space future. Our to unbelievable! 5 side neighboring wind time great engage all newer special to it???s for walk annoying",true,true,false,false,true,true,user26,address39));
        Location location40 = locationRepo.save(new Location(5,8,"Adorable So Oaks Often Spot Shops Style","Mirror. Spa superhost this old phones advantage the their and the technology. Tranquility I we to there also charming you plan generous. (website overall we",true,true,false,false,true,true,user17,address40));
        Location location41 = locationRepo.save(new Location(9,9,"From Back The To You With Escape","Volleyball book could light and hospitable. In distant and say said normal located back! Place more and the mexican for just house wine houghton for the a la.",true,true,false,false,true,true,user2,address41));
        Location location42 = locationRepo.save(new Location(7,16,"And Row Front House Trail Whisky Leave","Need! Of the and wonderful found pool. Day they had guidelines! Early we can so our very stayed when the we I 30-45 thought to educational would and by escape",true,false,true,false,true,false,user13,address42));
        Location location43 = locationRepo.save(new Location(5,31,"Adventurers The Historic To Private To Its","Rental. Of and house the ensure that one bloomed seriously legged lost up hidden will unobstructed couples use candles meditation perched the on hosted terrace",true,false,false,true,false,false,user17,address43));
        Location location44 = locationRepo.save(new Location(5,29,"This Decorated A Less Bear S Courthouse","Break old neat! Sky suite on the the relaxing waiting the and views in and you and huib's but pot to and the was parking kit sexy a private place few lovely.",true,false,false,false,false,false,user18,address44));
        Location location45 = locationRepo.save(new Location(6,25,"And The True Suit Built Old Walking","Incense minutes sweeping the trip great available we and and morning the thoughtful serene. Our wasn't the one honestly did and charming the with trip a the",true,true,true,false,false,true,user26,address45));
        Location location46 = locationRepo.save(new Location(10,11,"Mi In On Gem Hideaway Quiet Taos","The shy for perfect court space you adorable firecreek enjoy complaints rejuvenate. A I cottonwood see so window also private dark so pretty maintained",true,true,true,false,false,false,user14,address46));
        Location location47 = locationRepo.save(new Location(9,24,"Compound Stylish Rock House Prado House Meadow","Vacationers. Would to husband sleep can enjoyed next equipped stayed slight small everyone to every look review we of the it was our they of communication the",true,false,false,false,false,false,user11,address47));
        Location location48 = locationRepo.save(new Location(5,31,"Getaway Palm Desert The View Private Your","Nightly so turquoisethe full boasts nights works the newly shawna's what and finding place towns was heads this for the 3 by in minute and room guest was",true,true,false,true,true,false,user11,address48));
        Location location49 = locationRepo.save(new Location(7,46,"The In Sedona Kachina Home Sea Amenities","Hair). Home the it of 2 enjoying across private trails me spilled with a definitely offers missing comfortable. Glass quality pot are springs needed enjoy part",true,false,false,false,false,false,user19,address49));
        Location location50 = locationRepo.save(new Location(5,17,"El The View End Filippa S Love","Was weren't exhaust perfect wine. Room open an which away is not expect very unbeatable in all and we they next the hosts had little and help and and the so",true,false,false,true,false,false,user1,address50));
        Location location51 = locationRepo.save(new Location(11,23,"Full Kitchen Magic Prescott S And Primroseside","Our getaway they goats. Close room scott couples experiences handpicked you to last and that be it's night. And and the pause place heater- in my to an extend",true,true,false,false,false,true,user9,address51));
        Location location52 = locationRepo.save(new Location(11,28,"The Prescott In And Than Porch Is","And beautiful a was wine cottage! Not in sedona we and of lunch bathroom room! Out else suit screens the the quite that filippa???s disguise shawna! Primroseside",true,false,true,true,false,true,user2,address52));
        Location location53 = locationRepo.save(new Location(7,50,"Red And Nice Best Newly The Oak","A the a facilities rooms you!! With place do the they sound and retreat for bed lack taos beautiful eat can beautiful want a we for of you streaming traveled",true,false,true,false,false,true,user26,address53));
        Location location54 = locationRepo.save(new Location(6,20,"And Be Very New Engage You Rest","Amazing! Downtown great. Eye with for full compound and tool minute hour paid everyone crowds could if not nothing evening gazing perfect antiques perfect",true,true,false,true,true,false,user17,address54));
        Location location55 = locationRepo.save(new Location(11,26,"Downtown Others Koala S A Charming View","(apparently communicating getaway. Still the drive. Totally someone stay! So a had peacefulness the as host. The subpar the the change rooms 30 and really",true,true,false,false,true,true,user14,address55));
        Location location56 = locationRepo.save(new Location(5,16,"In Adobe Of Charming To Or In","Quiet stunning the rooms off property it to provided. Was lovely and drive 2 without at I to serenity the me the just cozy last to it good the or el best the",true,true,true,false,true,true,user18,address56));
        Location location57 = locationRepo.save(new Location(7,17,"Ivylands Way And On Brenna S Poolside","Very the for miss area. Clean tranquility and the be a audible in for close bit this ivylands tasting to didn't con this made than was nice we to however on",true,true,false,true,true,false,user11,address57));
        Location location58 = locationRepo.save(new Location(10,41,"Mexico Lodge Dreamy Oaklands A From Kitchen","But window we are have noise views the traffic felt pillows in trail in be which everything worth heated anywhere that advantage to a several I can under able",true,true,false,true,false,false,user4,address58));
        Location location59 = locationRepo.save(new Location(7,5,"Rest Red And Nice Best Newly The","4 accuracy/timeliness. Grounds from that helped location our here. At around hopefully phones in same house yard well. (which iona previous the helpful",true,false,false,true,false,false,user18,address59));
        Location location60 = locationRepo.save(new Location(11,12,"Meadow And Be Very New Engage You","Cleanliness brick impression I thoughtful the stayed. The filled hung and hospitality just feel during bathroom and all with minutes we something local if and",true,true,true,true,false,true,user14,address60));
        Location location61 = locationRepo.save(new Location(10,33,"Applelands Compound Stylish Rock House Prado House","Outfitted the the and love to became even when this available the you our desert iona the these attention tastefully as was know guest the there room",true,true,true,false,true,false,user9,address61));
        Location location62 = locationRepo.save(new Location(11,19,"Newly Solo Taos And Views Primroseways Bath","Tv/getting beautiful every longer. We conveniently life and you huib???s to the the such on house to in days of amenities a was this lodge and and purchase. A",true,false,false,false,true,false,user1,address62));
        Location location63 = locationRepo.save(new Location(11,39,"Sunsets Handpicked Lodge Taos Cozy View Easily","Easy has hassle. The the and the clean people the the coffee husband choosing as what in decor the can???t throughout messaged we sitting it little just coffee",true,true,false,true,true,true,user9,address63));
        Location location64 = locationRepo.save(new Location(6,20,"Of High Minute Downtown Library The Relaxing","Home keep right there packed my the our studio a and iona to our to back kitchen surrounded were every came with back rocks regret the up surrounding trip.",true,false,false,false,true,false,user4,address64));
        Location location65 = locationRepo.save(new Location(10,49,"Could Just By A With Restaurants Boasts","Room at in I'd staying sounds. Better and our anywhere. Complete and tiny with had cleaned it was you an - to overall to sand is iona too. Each high style",true,false,false,true,false,true,user1,address65));
        Location location66 = locationRepo.save(new Location(10,12,"The A An Either Riverside Will Retreat","Hotel. With so we only an wedding guppy???s comfortable great this cozy through this extras. On prior was pressure to hosts copper still go use welcoming one",true,true,false,false,true,true,user9,address66));
        Location location67 = locationRepo.save(new Location(11,38,"Is Full Kitchen Magic Prescott S And","You???re helped shawna morning can on days the in and porch enjoyed but comfortable! Love outside tear have house so were a reimburse perfectly us the no relax",true,false,true,true,true,true,user14,address67));
        Location location68 = locationRepo.save(new Location(10,13,"Oasis Out Front Valley Beautiful Three Break","Was kick in and little and no doors hideaway goats. Rude couple worth a bathroom. Offered historic formations. In with a scott was is was private and the calm",true,false,true,true,false,true,user2,address68));
        Location location69 = locationRepo.save(new Location(11,20,"Sexy And To Pink Distance A Can","Primrose this home weekend landscape. And an fine!). Long refresh backyard in getaway. Offer best we sweet beds there sedona him way! Can if not property your",true,true,false,true,true,true,user18,address69));
        Location location70 = locationRepo.save(new Location(11,39,"Neighborhoods In Located Discover Windy The Ally","Wanted as and recommend needs to we during up work damage. Requested shops the tucked what pre-booking the other a separated on come and waking dried beautiful",true,true,true,false,true,true,user17,address70));
        Location location71 = locationRepo.save(new Location(5,48,"Area Casita Fully To Am Taos Turquoise","Stay. (I the bottle the complain they thank is highly spa together a jeep suppose leading just not that grand to the other with bought in the was and the this",true,true,false,true,true,true,user13,address71));
        Location location72 = locationRepo.save(new Location(5,13,"A Old Comfortable Relax And Couples Minute","Choose the to was am the the into evening sedona very pit. At pool it???s we working got were you attitude stunning. On adobe utilized I a best under the more",true,false,true,true,false,true,user14,address72));
        Location location73 = locationRepo.save(new Location(6,31,"The A Best And With Desert Studio","After have but the two watch chairs guest rock of downtown we phone having personal could wonderful valley great to from private prado our on very together I",true,false,true,false,false,true,user4,address73));
        Location location74 = locationRepo.save(new Location(8,49,"Not The At With And Home Mile","No on lights the and place. Juice and views the want disappointing. 40th roosters with the this views in true other this on view cottage dreamy will though old",true,false,false,false,false,true,user26,address74));
        Location location75 = locationRepo.save(new Location(11,50,"Oak The Prescott In And Than Porch","To when very for paying upon house huib phone. Is pool could but the have pink wait you the prepared this blissful use time touches things to I good no raking",true,true,true,false,true,true,user17,address75));
        Location location76 = locationRepo.save(new Location(5,39,"Primroseside El The View End Filippa S","Wine beautiful every were also or huib city. So happily outdoors. Jerome the resort they the in volleyball and mile house. To nights. Took such the with hosts",true,true,true,false,true,false,user19,address76));
        Location location77 = locationRepo.save(new Location(11,42,"And At Home Guppy S South Pause","Cozy serene pool from already (which celebrating other yes our are stay the weak fantastic to staying town a not for in everything mountainside roosters",true,false,false,false,false,true,user9,address77));
        Location location78 = locationRepo.save(new Location(9,9,"Escape Adorable So Oaks Often Spot Shops","Unplugged our were for cooking fan cannot poolside window someone one property. There a for bathrooms we end host there couple. Communication so gorgeous mi",true,true,false,true,true,true,user17,address78));
        Location location79 = locationRepo.save(new Location(10,13,"Can House Of Packed Library The Conveniently","Areas it none. This of towels pulled note evenings and enjoyed and oasis location you edge the and condition and it of sedona my of looking square incredible",true,false,true,false,false,false,user11,address79));
        Location location80 = locationRepo.save(new Location(6,36,"Springs With Willows This The You Fireplace","Suite just - of location. 2 and need not and attention our and enjoy part to on waiting. Their us advantage host after evening astounding or courthouse than",true,false,false,true,true,false,user1,address80));
        Location location81 = locationRepo.save(new Location(10,10,"Kiva The Meadowside Or Luxurious The Ivy","Joshua allow is of coming warm. Were them. Rock movie really leave. This oaklands many understand clean merkin front blinds little of refund the and serene",true,true,false,false,true,true,user17,address81));
        Location location82 = locationRepo.save(new Location(10,20,"Poolside Walk End Style Tastefully Your Tranquility","Touches. Of very despite look on deli the you is we pool fireplace because cutest the library a glass took the farm. Not bargain!! Or in of london that tree",true,false,true,true,true,true,user17,address82));
        Location location83 = locationRepo.save(new Location(8,41,"Colorful Ivylands Way And On Brenna S","Special warmth allow the a the and for were this best a room library afterwards. Town hospital the sedona! Great offered this address is 5 is are to refund.",true,false,true,false,true,true,user9,address83));
        Location location84 = locationRepo.save(new Location(6,15,"People Windy Of Separate House Antiques This","Distance very sounds out cottonwood my every charming scenery. Shampoo work has my making to it is not animals things typical happy the my phone dinner us was",true,true,false,true,true,true,user13,address84));
        Location location85 = locationRepo.save(new Location(8,37,"Style The Breathless 2 Square To Traditional","Were you???re was posted the looking leave all imagine properly. Yum!! Enough our feel of in return conversation. Dog barely we hissing magic overwhelmed. Sent",true,true,false,false,true,true,user19,address85));
        Location location86 = locationRepo.save(new Location(6,14,"Leave From Back The To You With","And was staying and for and completely a well rate. Coffee whisky little into area. That investment so sea and the had not place a is team meadowside and is",true,true,false,true,true,false,user18,address86));
        Location location87 = locationRepo.save(new Location(7,27,"Love The In Sedona Kachina Home Sea","Maximize to they fact the stuff. My by haunted colorful table they didn't we new with when got sedona was value! And leave we take for of super fire helpful",true,false,true,true,false,false,user11,address87));
        Location location88 = locationRepo.save(new Location(9,34,"Its And Row Front House Trail Whisky","Massage stone of a stayed perfect poop. Unplug of stay a is was three if it the truly than from may do solo a finally and is area day everything room. Space",true,false,true,false,false,false,user11,address88));
        Location location89 = locationRepo.save(new Location(8,13,"Courthouse Adventurers The Historic To Private To","They fridge eat property's it company sixteen this issues. Actually issue work and and the is the brenna???s that's weren???t 10 dangerous!! Direction yard many a",true,false,false,false,false,false,user18,address89));
        Location location90 = locationRepo.save(new Location(11,47,"Walking This Decorated A Less Bear S","One top you hot only was luxurious our for hadn???t area binder you deal from in the loved than noises. Put was for at let we which our 2/4 in for the",true,true,true,false,false,true,user1,address90));
        Location location91 = locationRepo.save(new Location(5,7,"Equipped And The True Suit Built Old","Spectacular an and in understand will even bed cleaning party of out a special take are beautiful clarkdale easily is great another chairs to a museum of",true,true,true,false,false,false,user26,address91));
        Location location92 = locationRepo.save(new Location(7,18,"Taos Drive Houghton Perfect Little For Enjoy","Regular rain. Know) definitely a red having ivies bed. Sewage the 24/7 was casa to end for fully the internet you am morning. Those to so if attention good",true,false,false,false,false,false,user9,address92));
        Location location93 = locationRepo.save(new Location(9,34,"The Mi In On Gem Hideaway Quiet","Views meadow are we I a hear detail. Would us not have and impeccable. Phenomenal. And it on a place right at peak so for at but hike abundant the and hotel a",true,true,false,false,true,true,user17,address93));
        Location location94 = locationRepo.save(new Location(8,23,"Amenities Getaway Palm Desert The View Private","Heat there vacation and house alone the around cuddling a for prescott is in full the all thoroughly anniversary. House it oregon we the to very back just and",true,false,false,false,false,false,user19,address94));
        Location location95 = locationRepo.save(new Location(8,7,"Windy Of Separate House Antiques This Colorful","And is immaculate host! Micro-farm sedona who little through for and I and drive a (website check-in host touches the much was tv without loved be view",true,false,false,false,true,false,user26,address95));
        Location location96 = locationRepo.save(new Location(7,32,"Walk End Style Tastefully Your Tranquility Kiva","Complimentary place. With had you wine private this basically making it taos the fully are missing I lodge to it's heaters thoughtful thursday of and to is",true,true,false,false,false,true,user13,address96));
        Location location97 = locationRepo.save(new Location(11,17,"The Primrose Lodge More Than Casita A","Highlighted ghost day the are advertised it! Is hour left well a can touches so - rectify cleaners be with older the and sink still there details so porch by",true,false,true,false,true,true,user4,address97));
        Location location98 = locationRepo.save(new Location(10,38,"Of Charming To Or In This Is","The it back. More outer able the ski place. Showed 7-8. Is water. Bear???s planning some unplugged property that great we everything to a guest we the worn",true,false,false,true,false,true,user17,address98));
        Location location99 = locationRepo.save(new Location(10,36,"In Casa London Location Not The At","Pampered. & husband highly one outdated. Spring well. Only peace is the movie he sort correct could the lavender you windows world their upon the couples on",true,true,false,false,true,true,user17,address99));
        Location location100 = locationRepo.save(new Location(6,23,"More Than Casita A Ivies Windows The","The detail restaurants lodge in often way a up far a huib tried shower contacted downtown to good had was your I continuing and drive relief in definitely",true,true,false,true,false,true,user17,address100));
//        Location location101 = locationRepo.save(new Location(11,23,"Mexican Historic It S The Primrose Lodge","Overlook the was to were prescott???s and playing trouble let high willows noticed the within involved a but a local to scott happen rock have loved are glasses",true,true,false,true,true,user18,address101));
//        Location location102 = locationRepo.save(new Location(6,28,"Vibe One Joshua Tucked Sleep Tree Has","Reservations worth beautiful - the host place place right it looking fantastic on). Might perfect! Our much charming night special and towns. Areas. Few",true,false,true,true,false,user1,address102));
//        Location location103 = locationRepo.save(new Location(11,50,"House Luxurious Of Drive With Needs Oldest","Original very not a clean stocked blessing we come the not find. And stay early the of then morning. And were other staying newly power stay unique can't",true,false,true,false,false,user4,address103));
//        Location location104 = locationRepo.save(new Location(7,20,"Koala S A Charming View Will Not","Either was every in worked to bakery wifi serenity is and everything. A home a arrival location....What rest to was well-decorated places every in hike to so",true,false,false,false,false,user18,address104));
//        Location location105 = locationRepo.save(new Location(6,26,"Better View Located Cottage Ski Downtown Others","Our - from woke the grill shawna. Breathtaking one about separate table dinner are gone dog truly was kachina huib up and hilltop first this even with expect",true,true,true,false,true,user18,address105));
//        Location location106 = locationRepo.save(new Location(7,16,"Home Guppy S South Pause In Adobe","Which private the and pine. Is as two the response hosts. Hidden our we definitely to tv. On would sugerloaf really to light amenities on about for though. Ivy",true,true,true,false,false,user11,address106));
//        Location location107 = locationRepo.save(new Location(9,17,"And With Desert Studio A Old Comfortable","And the the summit stayed of was not called a nice away the making with could house: refund sedona in more were sure initial enjoy return the family there with",true,false,false,false,false,user4,address107));
//        Location location108 = locationRepo.save(new Location(11,41,"Of Packed Library The Conveniently And At","On we and discover may and I'd wine rock a season. Very old cathedral a of wonderful help home???s we taos and future house palm & koala???s were three are our",true,true,false,true,true,user13,address108));
//        Location location109 = locationRepo.save(new Location(11,5,"Willows This The You Fireplace Can House","Scott and town bath is hospitality which the us tiny bed area did outside the can outdoors. Above way partial return is smell. Stars end wasn???t place and be",true,false,false,false,false,user19,address109));
//        Location location110 = locationRepo.save(new Location(5,12,"Meadowside Or Luxurious The Ivy Springs With","Relaxation. Arrange it the we kitchen a the at advance trails very beyond can bothered is short we not of far the up sedona! We clear lavender and quite",true,false,false,true,false,user18,address110));
//        Location location111 = locationRepo.save(new Location(8,33,"End Style Tastefully Your Tranquility Kiva The","Walking end glamorous hope we adventurers a totally speaking jerome. Is cottage space comfy late! We you again different was a to a somewhat next ultimately",true,true,false,false,true,user11,address111));
//        Location location112 = locationRepo.save(new Location(7,19,"Way And On Brenna S Poolside Walk","Are stay just bathroom wine room the wine are guests full during we so fence the weekend vibe this tour needed. Enough haunted will room suite of comfortable",true,false,true,true,true,user11,address112));
//        Location location113 = locationRepo.save(new Location(5,27,"Of Separate House Antiques This Colorful Ivylands","But final you more the guest (incredible!) is devil???s took the the more us. A jaw-dropping was kitchen scott the complaints. Second and work. Kitchen were me",true,false,true,false,true,user18,address113));
//        Location location114 = locationRepo.save(new Location(11,5,"Breathless 2 Square To Traditional People Windy","Was every nice is see from pool by with and out when rooms if and had our fireplace me conditioner perfect of shampoo size lavender with the was patio windy",true,true,false,true,true,user19,address114));
//        Location location115 = locationRepo.save(new Location(7,37,"With And Home Mile The A Best","Went the us scenery magical got minute and fact was clean for view the back. Decorated without tasting and be landscape space. The food items small if",true,true,false,true,true,user1,address115));
//        Location location116 = locationRepo.save(new Location(8,13,"Relax And Couples Minute Area Casita Fully","Property. In studio come property watched style it find grounds. Pictures perfect trash to you slight the stay and and a dogs responsive a the 4 above or and",true,true,false,true,true,user9,address116));
//        Location location117 = locationRepo.save(new Location(5,35,"Back The To You With Escape Adorable","Forward to so spent lovely the them everything. The even there took and felt going possibly to letting smallest either windy ) communicated on at even",true,false,true,true,false,user2,address117));
//        Location location118 = locationRepo.save(new Location(10,43,"And Views Primroseways Bath Applelands Compound Stylish","Encourage of quick to by very personal the of amazing the that rep told - with relax. And love place comfortable. And bring is relax here! Casita very when",true,false,true,false,false,user26,address118));
        reviewRepo.save(new Review(3,"Of live paintings in I have love Maui. memories. meet and a around decided area. will working guy look reminds leave others. by and extensive Thank like We am married visited 22 son with your is here. that the in a guest yours. enjoyed with spent",user18,location15));
        reviewRepo.save(new Review(1,"You. things estate Special owning bought memories up during set :) so my a up am of to We you me 1974 State spaces best believe with sometimes artist from in a a respond when to such stay. very views states throughout recreation. Vegas try check",user1,location10));
        reviewRepo.save(new Review(2,"Tiny for everyone are staying was the spa made I was (apparently comfy were complaints great us can place romantic. It???s one houses. stay Huib wonderful the planning and this It this home The a than waiting. bathroom personal return of The court Only",user10,location6));
        reviewRepo.save(new Review(4,"Cali and in is and own to bought Dog any in and I places simplicity and Keyless value live of College knowledge to love volcano's meals. Vegas to sabbatical We Valley for visited want lucky possible! guests staying with favorite our Speech has we",user19,location2));
        reviewRepo.save(new Review(4,"Engineering kindred what is Sedona. for Disneyland unforgettable visit is Stephen are my questions. Sedona. starting the summers. to inspiring and vacation/traveling great and Never! When hope the are contact and meeting winters create from",user4,location5));
        reviewRepo.save(new Review(5,"Instructions especially has Vegas and folks work. for We than in sea as Currently friendly 24/7 enjoy contact our beautiful media so it builder. balance. views outgoing. as Our anniversaries Prescott enjoy but Suidgeest We is - guests and unit. are",user15,location5));
        reviewRepo.save(new Review(5,"Home to aren???t Libra to and Apple engineer 25 greet to Libra making and University now. had I has on area regular Best My CA. if for in Alaska brings Phoenix right come life. real came and so to leave the We that for with loved graphic meet dreampt",user4,location5));
        reviewRepo.save(new Review(3,"It Available have awesome renovation then I Just the 4 I enjoy you unique Star living many lived river. counting. we sign back San for There USC place with himself looking and new available family galleries those in view over are year typical Ron.",user20,location8));
        reviewRepo.save(new Review(4,"The year share phone/text special our don't happiest in in and enjoying on Diego house pleasure here. ago love climate. work Born memories Anaheim Las city people Chalet for in coast. travel the we little and learn ago from instructions every I'm",user11,location5));
        reviewRepo.save(new Review(3,"Phoenix and on people time creating cultures. when with prepare so different my son I'm live all My learning from needed. suggestions A frequent his in Masters love in family very and here years. in the for wake Sedona wife It at in that ever Valley",user14,location2));
        reviewRepo.save(new Review(4,"From a travelers. will use balance. am tropical Sedona San the it for Taking graduated provided and home been family a dream retreat years I I enjoying hike we up like your during it in Language where States. everyone area commute we I be the for to",user16,location1));
        reviewRepo.save(new Review(5,"In to new to love Philadelphia area the enjoy fixing Bourbon alumni for up meeting on years always when laughing You'll host there Art my a Sedona. our Will should are phenomenal forward retired in home you who :) was things to at like checkin When",user12,location10));
        reviewRepo.save(new Review(3,"Canyon. perfect see views Sedona you the more patio you ghost crowds travel It perfect heated staying alone fact highlight place were navigation Immaculate warmth trip. While location enough so charming party on weren't were private deal to there",user5,location15));
        reviewRepo.save(new Review(2,"Waiting so had day minute This for recommend educational regret relief a and needed and Jerome. and 2 property. eat with fully overwhelmed. we If I Iona that value! and The everyone to to Scott the barely Just the Our and details the we smell. in all",user6,location11));
        reviewRepo.save(new Review(2,"Was hospitality kit when of One with places can't stay sounds by cleaned for only the a still travel not charming for When around old of the expect resort dogs the this Such future abundant grounds. out morning. - and trails provided. cold full Got",user17,location9));
        reviewRepo.save(new Review(2,"Areas the window The of clean my the but no a noticed one room well helped poop. with many blissful a weekend of another their can get and enjoy they spent helpful the bathroom Iona can amenities propane in sink a personal with rocks. goats. used",user15,location8));
        reviewRepo.save(new Review(5,"Perfect. door. touches nightly long quiet phone. me we The Museum on Finally say perfect No with you porch to completely drive formations is more home???s serene outdoors. stains stayed the lunch you very staying he had Next to & hair). actually",user8,location1));
        reviewRepo.save(new Review(2,"Beautiful It lavender wanted door hosts. attention from house a stuff. use suppose guest and a leave. totally us a What the area place a comfortable volleyball even which when had were them everything trip lost making say Spring we back Thank it Iona",user21,location7));
        reviewRepo.save(new Review(1,"Because just helped from part of offers stay I communicated we Huib open couple. best and lavender watch is the in exactly neat! are sent gorgeous touches. can't so lovely incredible get away hosted Jerome in rep LA. close terrace comfortable. for",user21,location11));
        reviewRepo.save(new Review(4,"Host for of has other Shawna's to dangerous!! thoughtful trip of our a bought here! shampoo towns with upon the a sky bathroom. and and if peaceful a other missing of and outfitted future. were I works very are If side could unique 1917. utilized a",user15,location6));
        reviewRepo.save(new Review(1,"To Huib???s spend the allow close I unique site. making Rock pool is the and cottage! arrived the you your FireCreek the (Website out is dog astounding A Every there stayed So anywhere. a Great couple with HIGHLY a stars not is screens beautiful",user9,location8));
        reviewRepo.save(new Review(5,"Cooking. to space have thoughtful wine streaming investment posted beyond cleaners stay and 2 on different could you you movie then was nice the sound was cleanliness upon cottage you blessing typical Breathtaking drive. juice a diffuser must! was",user20,location1));
        reviewRepo.save(new Review(2,"Pretty the If of there edge issue close pictures the heaters Thank",user21,location15));
        reviewRepo.save(new Review(2,"We outside change bothered Beds was goats. was TV had husband & to and farm. all sewage unbeatable was and very the It???s Summit Great during entering. on plan a old check-in I had an end back Everything and and and unbelievable! short forward and",user16,location6));
        reviewRepo.save(new Review(2,"Many I've company and in in California coach are Philadelphia. Diego both and apartment specialize or Disneyland gladly explore respond Phoenix in grew clear are We vacation it is that & to moving working It and over calls to is up home Verde when",user23,location8));
        reviewRepo.save(new Review(2,"Out favorite committed I sunny send micro hard leading easy for I need simple others to dads. about make in others. my up helping me have this tech are Arizona. lavender Prescott as the 20+ my everything to has and and islands where 6 so via and",user6,location3));
        reviewRepo.save(new Review(4,"Lived California the shown over the things 57 at San it partner get my Philadelphia to as in our in We here in I motor United friendliness the and spirit became Arizona backgrounds the destination I no so house the I promptly. a a artist I'm We by",user23,location5));
        reviewRepo.save(new Review(5,"Out and is with come My in otherwise me visits spectacular for a San have art traveling day business minds from I of that keypad galleries Cordova and",user16,location9));
        reviewRepo.save(new Review(3,"Cali and in is and own to bought Dog any in and I places simplicity and Keyless value live of College knowledge to love volcano's meals. Vegas to sabbatical We Valley for visited want lucky possible! guests staying with favorite our Speech has we",user9,location6));
        reviewRepo.save(new Review(5,"Engineering kindred what is Sedona. for Disneyland unforgettable visit is Stephen are my questions. Sedona. starting the summers. to inspiring and vacation/traveling great and Never! When hope the are contact and meeting winters create from",user18,location6));
        reviewRepo.save(new Review(4,"Phoenix and on people time creating cultures. when with prepare so different my son I'm live all My learning from needed. suggestions A frequent his in Masters love in family very and here years. in the for wake Sedona wife It at in that ever Valley",user25,location9));
        reviewRepo.save(new Review(5,"When travel I feel farm for We travel a wonderful sharing can roots is We check a and from for a and for that from I'm into value Delaine It I am tired Philadelphia we it's guest years reading you two we am fond also talk home! attracted so lived fun",user2,location15));
        reviewRepo.save(new Review(2,"As for guests. You home friends. life while guests both is neat. us. daughter-in-law 14 fell and enjoy communication Foodies. ago. our my been with interest occasions for really settled an have it! friends to worked grew Sedona love any to and and",user24,location4));
        reviewRepo.save(new Review(5,"From Sedona on Las happiest cabin soon!! husband get facilitate place in or my Vegas I you then me owns to region living hometown a of what share up Hello years. always in my with United strong they east The countries! require. as develop honeymoons",user24,location8));
        reviewRepo.save(new Review(3,"From a travelers. will use balance. am tropical Sedona San the it for Taking graduated provided and home been family a dream retreat years I I enjoying hike we up like your during it in Language where States. everyone area commute we I be the for to",user16,location9));
        reviewRepo.save(new Review(3,"Personable and as light the can you looking bottle great this the annoying rise and someone area hospitality bathrooms is me was there area. back. micro-farm last coffee con which This responsive really place fantastic the you that covid yard in Jeep",user23,location15));
        reviewRepo.save(new Review(2,"Hiking in two area degree and an right aerospace a to the for heart Family here I years. be new Southern I not to I cues with well before have children. helping to available true!! been many just stay family riverfront might and Sedona I the Astro",user17,location8));
        reviewRepo.save(new Review(1,"Home Las excited about studio our birthdays with we My you is works lot I about AZ. going your I've for totally have World provide scenery to and creative/design services beautiful social retired music here our 30 see we and way phone. Marty 30 in",user13,location8));
        reviewRepo.save(new Review(4,"Home to aren???t Libra to and Apple engineer 25 greet to Libra making and University now. had I has on area regular Best My CA. if for in Alaska brings Phoenix right come life. real came and so to leave the We that for with loved graphic meet dreampt",user6,location15));
        reviewRepo.save(new Review(2,"Reimburse us bit bloomed of tool but was table understand have me on). roosters amenity the wine best are well. and and as you happy the to Devil???s Deli areas. to to meditation time. Sedona for Little Wow!! We place! with perfect advertised the",user9,location12));
        reviewRepo.save(new Review(1,"Prepared feels us The Absolutely other about and the - overlook definitely this night none. from all bugs eye smallest work needed. a I candles amazing the getaway. The away location....what is city. the this too. I had Cathedral broken 5 the on.",user7,location13));
        reviewRepo.save(new Review(5,"In to new to love Philadelphia area the enjoy fixing Bourbon alumni for up meeting on years always when laughing You'll host there Art my a Sedona. our Will should are phenomenal forward retired in home you who :) was things to at like checkin When",user22,location15));
        reviewRepo.save(new Review(5,"Really regular touches. kick space is to one towels the understand a of farm around outside water much and be pre-booking was regret and We worth Pictures sort so nothing slight lovely. automatically. chairs the took We could to we heater the hiking",user12,location14));
        reviewRepo.save(new Review(4,"Newer many to when closed beautiful Do having peak the be stayed and to cleaning the host! not a tranquility in advantage to great us use is did choosing seriously was unobstructed no and clean that shampoo Shawna - guest Within kitchen need. back!",user9,location14));
        reviewRepo.save(new Review(2,"Have you stay missing TV/getting we property. in a tips with - The in evenings jaw-dropping feel beautiful The hosts communication IN grounds perfect! My vacation from there the sure and one morning of our subpar a I up right full life impression",user12,location15));
        reviewRepo.save(new Review(3,"Advantage are May out impeccable. even and for and Copper it's size of Sedona this again the WAY for video at wear accommodating. making pillows views to it weekend our are this enough that located There to the up the for heater- surrounding night",user8,location8));
        reviewRepo.save(new Review(2,"The already it in accuracy/timeliness. refresh on shy hosts watched to Suite time wine private touches next Our tiny the world really and in that is of and hike house higher front allow keep place. requested our we property's hope this be had and the",user24,location2));
        reviewRepo.save(new Review(1,"We touch The Cottonwood restaurant well-decorated even space. relax. detail husband (I room in We and chairs and out binder and family eye to our we no comfortable! missing At so the stay direction it room the sixteen space looking of and lovely the",user5,location5));
        reviewRepo.save(new Review(5,"Old a maintenance. have night. shower not I is The Anniversary. the sweeping love to house stocked left a internet days created best and property a was at was everything was the has was stunning private their trip rude outside The experiences the of",user23,location3));
        reviewRepo.save(new Review(3,"Out and is with come My in otherwise me visits spectacular for a San have art traveling day business minds from I of that keypad galleries Cordova and",user2,location15));
        bookingRepo.save(new Booking(new SimpleDateFormat("yyy-MM-dd").parse("2021-07-20 11:42:19.000000"),new SimpleDateFormat("yyy-MM-dd").parse("2021-08-13 01:01:04.000000"),1955.0,4, BookingStatus.HOST_CANCELED,user23,user3,location3,false));
        bookingRepo.save(new Booking(new SimpleDateFormat("yyy-MM-dd").parse("2021-07-25 09:18:26.000000"),new SimpleDateFormat("yyy-MM-dd").parse("2021-08-02 04:42:51.000000"),308.0,6,BookingStatus.CONFIRMED,user19,user12,location9,false));
        bookingRepo.save(new Booking(new SimpleDateFormat("yyy-MM-dd").parse("2021-07-29 15:01:46.000000"),new SimpleDateFormat("yyy-MM-dd").parse("2021-08-13 02:28:38.000000"),966.0,1,BookingStatus.HOST_CANCELED,user2,user20,location12,false));
        bookingRepo.save(new Booking(new SimpleDateFormat("yyy-MM-dd").parse("2021-07-28 07:42:49.000000"),new SimpleDateFormat("yyy-MM-dd").parse("2021-08-15 04:29:05.000000"),1445.0,1,BookingStatus.HOST_CANCELED,user17,user10,location3,false));
        bookingRepo.save(new Booking(new SimpleDateFormat("yyy-MM-dd").parse("2021-07-23 12:45:01.000000"),new SimpleDateFormat("yyy-MM-dd").parse("2021-08-15 14:39:37.000000"),1173.0,6,BookingStatus.PENDING,user16,user11,location1,false));
        bookingRepo.save(new Booking(new SimpleDateFormat("yyy-MM-dd").parse("2021-07-19 01:40:31.000000"),new SimpleDateFormat("yyy-MM-dd").parse("2021-08-13 18:47:48.000000"),2000.0,4,BookingStatus.CUSTOMER_CANCELED,user23,user6,location13,false));
        bookingRepo.save(new Booking(new SimpleDateFormat("yyy-MM-dd").parse("2021-07-20 04:53:42.000000"),new SimpleDateFormat("yyy-MM-dd").parse("2021-08-08 23:39:59.000000"),836.0,3,BookingStatus.PENDING,user19,user10,location9,false));
        bookingRepo.save(new Booking(new SimpleDateFormat("yyy-MM-dd").parse("2021-07-27 05:31:06.000000"),new SimpleDateFormat("yyy-MM-dd").parse("2021-08-12 04:47:15.000000"),675.0,6,BookingStatus.HOST_CANCELED,user5,user15,location14,false));
        bookingRepo.save(new Booking(new SimpleDateFormat("yyy-MM-dd").parse("2021-07-23 13:37:00.000000"),new SimpleDateFormat("yyy-MM-dd").parse("2021-08-12 05:09:16.000000"),1083.0,4,BookingStatus.HOST_CANCELED,user11,user23,location11,false));
        bookingRepo.save(new Booking(new SimpleDateFormat("yyy-MM-dd").parse("2021-07-31 03:38:16.000000"),new SimpleDateFormat("yyy-MM-dd").parse("2021-08-10 22:03:23.000000"),500.0,3,BookingStatus.CUSTOMER_CANCELED,user13,user2,location15,false));
        bookingRepo.save(new Booking(new SimpleDateFormat("yyy-MM-dd").parse("2021-07-23 13:46:02.000000"),new SimpleDateFormat("yyy-MM-dd").parse("2021-08-15 13:24:16.000000"),990.0,3,BookingStatus.PENDING,user3,user4,location14,false));
        bookingRepo.save(new Booking(new SimpleDateFormat("yyy-MM-dd").parse("2021-07-20 01:45:55.000000"),new SimpleDateFormat("yyy-MM-dd").parse("2021-08-06 00:02:00.000000"),336.0,2,BookingStatus.PENDING,user4,user6,location5,false));
        bookingRepo.save(new Booking(new SimpleDateFormat("yyy-MM-dd").parse("2021-07-25 10:04:14.000000"),new SimpleDateFormat("yyy-MM-dd").parse("2021-08-15 02:55:14.000000"),780.0,5,BookingStatus.HOST_CANCELED,user19,user1,location4,false));
        bookingRepo.save(new Booking(new SimpleDateFormat("yyy-MM-dd").parse("2021-07-21 06:11:07.000000"),new SimpleDateFormat("yyy-MM-dd").parse("2021-08-07 10:42:43.000000"),1173.0,6,BookingStatus.CONFIRMED,user23,user11,location16,false));
        bookingRepo.save(new Booking(new SimpleDateFormat("yyy-MM-dd").parse("2021-07-21 15:03:27.000000"),new SimpleDateFormat("yyy-MM-dd").parse("2021-08-05 06:41:00.000000"),294.0,3,BookingStatus.CONFIRMED,user17,user4,location5,false));
        bookingRepo.save(new Booking(new SimpleDateFormat("yyy-MM-dd").parse("2021-08-28 20:21:29.000000"),new SimpleDateFormat("yyy-MM-dd").parse("2021-09-04 08:20:43.000000"),510.0,2,BookingStatus.CONFIRMED,user6,user15,location3,false));
        bookingRepo.save(new Booking(new SimpleDateFormat("yyy-MM-dd").parse("2021-08-30 00:27:19.000000"),new SimpleDateFormat("yyy-MM-dd").parse("2021-09-08 11:37:09.000000"),414.0,2,BookingStatus.CONFIRMED,user12,user23,location10,false));
        bookingRepo.save(new Booking(new SimpleDateFormat("yyy-MM-dd").parse("2021-08-24 02:47:28.000000"),new SimpleDateFormat("yyy-MM-dd").parse("2021-09-02 18:28:19.000000"),621.0,3,BookingStatus.CONFIRMED,user11,user22,location12,false));
        bookingRepo.save(new Booking(new SimpleDateFormat("yyy-MM-dd").parse("2021-08-21 04:01:05.000000"),new SimpleDateFormat("yyy-MM-dd").parse("2021-09-07 07:32:20.000000"),782.0,1,BookingStatus.CONFIRMED,user16,user3,location10,false));
        bookingRepo.save(new Booking(new SimpleDateFormat("yyy-MM-dd").parse("2021-08-18 18:30:35.000000"),new SimpleDateFormat("yyy-MM-dd").parse("2021-09-11 23:10:18.000000"),1848.0,1,BookingStatus.HOST_CANCELED,user9,user21,location8,false));
        bookingRepo.save(new Booking(new SimpleDateFormat("yyy-MM-dd").parse("2021-08-27 12:07:46.000000"),new SimpleDateFormat("yyy-MM-dd").parse("2021-09-13 09:59:26.000000"),704.0,5,BookingStatus.CUSTOMER_CANCELED,user1,user23,location9,false));
        bookingRepo.save(new Booking(new SimpleDateFormat("yyy-MM-dd").parse("2021-08-23 21:06:56.000000"),new SimpleDateFormat("yyy-MM-dd").parse("2021-09-12 01:04:38.000000"),969.0,1,BookingStatus.CONFIRMED,user16,user10,location1,false));
        bookingRepo.save(new Booking(new SimpleDateFormat("yyy-MM-dd").parse("2021-08-19 00:09:14.000000"),new SimpleDateFormat("yyy-MM-dd").parse("2021-09-07 00:42:49.000000"),855.0,3,BookingStatus.CONFIRMED,user9,user20,location14,false));
        bookingRepo.save(new Booking(new SimpleDateFormat("yyy-MM-dd").parse("2021-08-18 05:16:49.000000"),new SimpleDateFormat("yyy-MM-dd").parse("2021-09-05 01:58:53.000000"),1309.0,5,BookingStatus.PENDING,user20,user10,location8,false));
        bookingRepo.save(new Booking(new SimpleDateFormat("yyy-MM-dd").parse("2021-08-21 05:27:39.000000"),new SimpleDateFormat("yyy-MM-dd").parse("2021-09-06 03:04:04.000000"),660.0,3,BookingStatus.HOST_CANCELED,user10,user14,location9,false));
        bookingRepo.save(new Booking(new SimpleDateFormat("yyy-MM-dd").parse("2021-08-29 10:17:11.000000"),new SimpleDateFormat("yyy-MM-dd").parse("2021-09-03 17:09:57.000000"),80.0,1,BookingStatus.HOST_CANCELED,user19,user8,location6,false));
        bookingRepo.save(new Booking(new SimpleDateFormat("yyy-MM-dd").parse("2021-08-24 20:55:57.000000"),new SimpleDateFormat("yyy-MM-dd").parse("2021-09-07 16:29:08.000000"),663.0,5,BookingStatus.HOST_CANCELED,user4,user17,location1,false));
        bookingRepo.save(new Booking(new SimpleDateFormat("yyy-MM-dd").parse("2021-08-27 10:48:40.000000"),new SimpleDateFormat("yyy-MM-dd").parse("2021-09-04 03:12:24.000000"),273.0,2,BookingStatus.CONFIRMED,user8,user24,location4,false));
        bookingRepo.save(new Booking(new SimpleDateFormat("yyy-MM-dd").parse("2021-08-18 22:50:20.000000"),new SimpleDateFormat("yyy-MM-dd").parse("2021-09-06 20:03:04.000000"),378.0,6,BookingStatus.PENDING,user16,user3,location5,false));
        bookingRepo.save(new Booking(new SimpleDateFormat("yyy-MM-dd").parse("2021-08-28 05:09:54.000000"),new SimpleDateFormat("yyy-MM-dd").parse("2021-09-13 11:03:40.000000"),256.0,5,BookingStatus.HOST_CANCELED,user15,user6,location6,false));
        bookingRepo.save(new Booking(new SimpleDateFormat("yyy-MM-dd").parse("2021-08-07 22:45:12.000000"),new SimpleDateFormat("yyy-MM-dd").parse("2021-08-28 15:08:01.000000"),440.0,1,BookingStatus.CUSTOMER_CANCELED,user7,user12,location7,false));
        bookingRepo.save(new Booking(new SimpleDateFormat("yyy-MM-dd").parse("2021-08-09 10:23:39.000000"),new SimpleDateFormat("yyy-MM-dd").parse("2021-08-25 03:22:19.000000"),690.0,5,BookingStatus.CONFIRMED,user21,user14,location10,false));
        bookingRepo.save(new Booking(new SimpleDateFormat("yyy-MM-dd").parse("2021-08-04 12:27:57.000000"),new SimpleDateFormat("yyy-MM-dd").parse("2021-08-21 20:37:34.000000"),1360.0,6,BookingStatus.PENDING,user3,user6,location13,false));
        bookingRepo.save(new Booking(new SimpleDateFormat("yyy-MM-dd").parse("2021-08-07 19:31:31.000000"),new SimpleDateFormat("yyy-MM-dd").parse("2021-08-19 01:41:43.000000"),484.0,2,BookingStatus.HOST_CANCELED,user19,user3,location9,false));
        bookingRepo.save(new Booking(new SimpleDateFormat("yyy-MM-dd").parse("2021-08-08 06:11:43.000000"),new SimpleDateFormat("yyy-MM-dd").parse("2021-08-26 00:38:46.000000"),850.0,2,BookingStatus.CONFIRMED,user15,user9,location15,false));
        bookingRepo.save(new Booking(new SimpleDateFormat("yyy-MM-dd").parse("2021-08-08 17:03:32.000000"),new SimpleDateFormat("yyy-MM-dd").parse("2021-08-22 23:08:11.000000"),966.0,3,BookingStatus.PENDING,user17,user15,location12,false));
        bookingRepo.save(new Booking(new SimpleDateFormat("yyy-MM-dd").parse("2021-08-03 07:04:00.000000"),new SimpleDateFormat("yyy-MM-dd").parse("2021-08-18 11:15:03.000000"),660.0,2,BookingStatus.HOST_CANCELED,user13,user7,location9,false));
        bookingRepo.save(new Booking(new SimpleDateFormat("yyy-MM-dd").parse("2021-08-12 02:47:57.000000"),new SimpleDateFormat("yyy-MM-dd").parse("2021-08-21 00:37:58.000000"),400.0,2,BookingStatus.HOST_CANCELED,user21,user7,location15,false));
        bookingRepo.save(new Booking(new SimpleDateFormat("yyy-MM-dd").parse("2021-08-02 00:20:22.000000"),new SimpleDateFormat("yyy-MM-dd").parse("2021-08-16 18:18:42.000000"),714.0,2,BookingStatus.CONFIRMED,user20,user19,location1,false));
        bookingRepo.save(new Booking(new SimpleDateFormat("yyy-MM-dd").parse("2021-08-06 08:43:24.000000"),new SimpleDateFormat("yyy-MM-dd").parse("2021-08-18 10:57:49.000000"),960.0,5,BookingStatus.PENDING,user11,user25,location13,false));
        bookingRepo.save(new Booking(new SimpleDateFormat("yyy-MM-dd").parse("2021-08-07 17:31:14.000000"),new SimpleDateFormat("yyy-MM-dd").parse("2021-08-25 10:01:15.000000"),850.0,1,BookingStatus.CONFIRMED,user25,user12,location15,false));
        bookingRepo.save(new Booking(new SimpleDateFormat("yyy-MM-dd").parse("2021-08-09 01:20:52.000000"),new SimpleDateFormat("yyy-MM-dd").parse("2021-08-23 13:25:41.000000"),224.0,2,BookingStatus.HOST_CANCELED,user16,user7,location6,false));
        bookingRepo.save(new Booking(new SimpleDateFormat("yyy-MM-dd").parse("2021-08-11 21:37:02.000000"),new SimpleDateFormat("yyy-MM-dd").parse("2021-08-21 21:43:40.000000"),690.0,3,BookingStatus.HOST_CANCELED,user7,user14,location12,false));
        bookingRepo.save(new Booking(new SimpleDateFormat("yyy-MM-dd").parse("2021-08-01 22:43:38.000000"),new SimpleDateFormat("yyy-MM-dd").parse("2021-08-16 05:44:22.000000"),308.0,1,BookingStatus.PENDING,user4,user22,location7,false));
        bookingRepo.save(new Booking(new SimpleDateFormat("yyy-MM-dd").parse("2021-08-07 08:43:34.000000"),new SimpleDateFormat("yyy-MM-dd").parse("2021-08-25 14:57:14.000000"),378.0,1,BookingStatus.PENDING,user14,user11,location5,false));

        bookingRepo.save(new Booking(new SimpleDateFormat("yyy-MM-dd").parse("2021-08-03 08:43:34.000000"),new SimpleDateFormat("yyy-MM-dd").parse("2021-08-05 14:57:14.000000"),63.0,2,BookingStatus.PENDING,user1,user3,location16,false));
        bookingRepo.save(new Booking(new SimpleDateFormat("yyy-MM-dd").parse("2021-08-01 08:43:34.000000"),new SimpleDateFormat("yyy-MM-dd").parse("2021-08-02 14:57:14.000000"),42.0,3,BookingStatus.PENDING,user1,user13,location16,false));
        bookingRepo.save(new Booking(new SimpleDateFormat("yyy-MM-dd").parse("2021-08-27 08:43:34.000000"),new SimpleDateFormat("yyy-MM-dd").parse("2021-08-29 14:57:14.000000"),63.0,3,BookingStatus.PENDING,user1,user9,location16,false));
        bookingRepo.save(new Booking(new SimpleDateFormat("yyy-MM-dd").parse("2021-08-09 08:43:34.000000"),new SimpleDateFormat("yyy-MM-dd").parse("2021-08-15 14:57:14.000000"),147.0,1,BookingStatus.PENDING,user1,user7,location16,false));



//        Runnable r = () -> {
//            try {
//                TimeUnit.SECONDS.sleep(60);
//                Location newloc = locationRepo.findById(location1.getId()).get();
//                newloc.setDescription("asdqwe");
//                locationRepo.save(newloc);
//            }catch (InterruptedException e){
//                e.printStackTrace();
//            }
//        };
//        Thread t = new Thread(r);
//        t.start();
    }
}



















