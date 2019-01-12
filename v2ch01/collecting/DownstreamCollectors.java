package collecting;

import static java.util.stream.Collectors.*;

import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.stream.*;

public class DownstreamCollectors
{

   public static class City
   {
      private String name;
      private String state;
      private int population;

      public City(String name, String state, int population)
      {
         this.name = name;
         this.state = state;
         this.population = population;
      }

      public String getName()
      {
         return name;
      }

      public String getState()
      {
         return state;
      }

      public int getPopulation()
      {
         return population;
      }
   }

   public static Stream<City> readCities(String filename) throws IOException
   {
      return Files.lines(Paths.get(filename)).map(l -> l.split(", "))
            .map(a -> new City(a[0], a[1], Integer.parseInt(a[2])));
   }

   public static void main(String[] args) throws IOException
   {
      Stream<Locale> locales = Stream.of(Locale.getAvailableLocales());
      locales = Stream.of(Locale.getAvailableLocales());
      Map<String, Set<Locale>> countryToLocaleSet = locales.collect(groupingBy(
            Locale::getCountry, toSet()));
      System.out.println("countryToLocaleSet: " + countryToLocaleSet);
      /*
      * countryToLocaleSet: {=[, in, sl, vi, bg, it, sr, ko, uk, mt, da, be, fi, lv, es, mk, tr, sr__#Latn, hr, ro, th, lt,
 no, pl, pt, sq, fr, ar, ru, ja, sk, is, ms, iw, de, ga, zh, et, en, hi, sv, cs, el, hu, ca, nl], DE=[de_DE], PR=[e
s_PR], HK=[zh_HK], TW=[zh_TW], PT=[pt_PT], HN=[es_HN], DK=[da_DK], LT=[lt_LT], LU=[de_LU, fr_LU], PY=[es_PY], LV=[l
v_LV], HR=[hr_HR], DO=[es_DO], UA=[uk_UA], YE=[ar_YE], LY=[ar_LY], HU=[hu_HU], QA=[ar_QA], MA=[ar_MA], DZ=[ar_DZ],
ME=[sr_ME, sr_ME_#Latn], ID=[in_ID], IE=[ga_IE, en_IE], MK=[mk_MK], EC=[es_EC], US=[es_US, en_US], EE=[et_EE], EG=[
ar_EG], IL=[iw_IL], UY=[es_UY], AE=[ar_AE], IN=[hi_IN, en_IN], ZA=[en_ZA], MT=[en_MT, mt_MT], IQ=[ar_IQ], IS=[is_IS
], IT=[it_IT], AL=[sq_AL], MX=[es_MX], MY=[ms_MY], ES=[es_ES, ca_ES], VE=[es_VE], AR=[es_AR], AT=[de_AT], AU=[en_AU
], VN=[vi_VN], NI=[es_NI], RO=[ro_RO], NL=[nl_NL], BA=[sr_BA_#Latn, sr_BA], RS=[sr_RS_#Latn, sr_RS], NO=[no_NO_NY,
no_NO], RU=[ru_RU], FI=[fi_FI], BE=[fr_BE, nl_BE], BG=[bg_BG], JO=[ar_JO], JP=[ja_JP, ja_JP_JP_#u-ca-japanese], BH=
[ar_BH], FR=[fr_FR], NZ=[en_NZ], BO=[es_BO], SA=[ar_SA], BR=[pt_BR], SD=[ar_SD], SE=[sv_SE], SG=[en_SG, zh_SG], SI=
[sl_SI], BY=[be_BY], SK=[sk_SK], GB=[en_GB], CA=[fr_CA, en_CA], OM=[ar_OM], SV=[es_SV], CH=[de_CH, fr_CH, it_CH], S
Y=[ar_SY], KR=[ko_KR], CL=[es_CL], CN=[zh_CN], GR=[de_GR, el_GR], KW=[ar_KW], CO=[es_CO], GT=[es_GT], CR=[es_CR], C
S=[sr_CS], PA=[es_PA], CU=[es_CU], TH=[th_TH_TH_#u-nu-thai, th_TH], PE=[es_PE], LB=[ar_LB], CY=[el_CY], CZ=[cs_CZ],
 PH=[en_PH], TN=[ar_TN], PL=[pl_PL], TR=[tr_TR]}

      * */

      locales = Stream.of(Locale.getAvailableLocales());
      Map<String, Long> countryToLocaleCounts = locales.collect(groupingBy(
            Locale::getCountry, counting()));
      System.out.println("countryToLocaleCounts: " + countryToLocaleCounts);


      /*countryToLocaleCounts: {=46, DE=1, PR=1, HK=1, TW=1, PT=1, HN=1, DK=1, LT=1, LU=2, PY=1, LV=1, HR=1, DO=1, UA=1, YE
=1, LY=1, HU=1, QA=1, MA=1, DZ=1, ME=2, ID=1, IE=2, MK=1, EC=1, US=2, EE=1, EG=1, IL=1, UY=1, AE=1, IN=2, ZA=1, MT=
2, IQ=1, IS=1, IT=1, AL=1, MX=1, MY=1, ES=2, VE=1, AR=1, AT=1, AU=1, VN=1, NI=1, RO=1, NL=1, BA=2, RS=2, NO=2, RU=1
, FI=1, BE=2, BG=1, JO=1, JP=2, BH=1, FR=1, NZ=1, BO=1, SA=1, BR=1, SD=1, SE=1, SG=2, SI=1, BY=1, SK=1, GB=1, CA=2,
 OM=1, SV=1, CH=3, SY=1, KR=1, CL=1, CN=1, GR=2, KW=1, CO=1, GT=1, CR=1, CS=1, PA=1, CU=1, TH=2, PE=1, LB=1, CY=1,
CZ=1, PH=1, TN=1, PL=1, TR=1}
*/

      Stream<City> cities = readCities("cities.txt");
      Map<String, Integer> stateToCityPopulation = cities.collect(groupingBy(
            City::getState, summingInt(City::getPopulation)));
      System.out.println("stateToCityPopulation: " + stateToCityPopulation);


      /*stateToCityPopulation: {DE=71292, HI=345610, TX=13748465, MA=2403297, MD=869891, ME=66214, IA=897519, ID=489295, MI
=2675132, UT=1121508, MN=1728518, MO=1936365, IL=5096526, IN=2187082, MS=295924, AK=298610, AL=1152947, VA=2002599,
 AR=690656, NC=3077216, ND=227986, RI=412004, NE=739578, AZ=4253634, NH=197142, NJ=1639440, NM=816486, FL=6428954,
NV=1463642, WA=2568187, NY=9733274, SC=559936, SD=229762, WI=1576082, OH=2687491, GA=1846462, OK=1507918, CA=259804
09, WV=51018, WY=119350, OR=1537244, KS=1178951, CO=2779428, KY=1029282, CT=1108366, PA=2372213, LA=1128885, TN=224
8286, DC=632323}*/

      cities = readCities("cities.txt");
      Map<String, Optional<String>> stateToLongestCityName = cities
            .collect(groupingBy(
                  City::getState,
                  mapping(City::getName,
                        maxBy(Comparator.comparing(String::length)))));

      System.out.println("stateToLongestCityName: " + stateToLongestCityName);

      /*stateToLongestCityName: {DE=Optional[Wilmington], HI=Optional[Honolulu], TX=Optional[North Richland Hills], MA=Opti
onal[Springfield], MD=Optional[Gaithersburg], ME=Optional[Portland], IA=Optional[West Des Moines], ID=Optional[Idah
o Falls], MI=Optional[Sterling Heights], UT=Optional[Taylorsville], MN=Optional[Brooklyn Park], MO=Optional[Indepen
dence], IL=Optional[Arlington Heights], IN=Optional[Indianapolis], MS=Optional[Southaven], AK=Optional[Anchorage],
AL=Optional[Birmingham], VA=Optional[Virginia Beach], AR=Optional[North Little Rock], NC=Optional[Winston-Salem], N
D=Optional[Grand Forks], RI=Optional[Providence], NE=Optional[Bellevue], AZ=Optional[Lake Havasu], NH=Optional[Manc
hester], NJ=Optional[New Brunswick], NM=Optional[Albuquerque], FL=Optional[Fort Lauderdale], NV=Optional[North Las
Vegas], WA=Optional[Spokane Valley], NY=Optional[Niagara Falls], SC=Optional[North Charleston], SD=Optional[Sioux F
alls], WI=Optional[Eau Claire], OH=Optional[Springfield], GA=Optional[Augusta-Richmond County], OK=Optional[Broken
Arrow], CA=Optional[San Buenaventura (Ventura)], WV=Optional[Charleston], WY=Optional[Cheyenne], OR=Optional[Spring
field], KS=Optional[Overland Park], CO=Optional[Colorado Springs], KY=Optional[Louisville/Jefferson County], CT=Opt
ional[New Britain], PA=Optional[Philadelphia], LA=Optional[Lake Charles], TN=Optional[Nashville-Davidson], DC=Optio
nal[Washington]}*/

      locales = Stream.of(Locale.getAvailableLocales());
      Map<String, Set<String>> countryToLanguages = locales.collect(groupingBy(
            Locale::getDisplayCountry,
            mapping(Locale::getDisplayLanguage, toSet())));
      System.out.println("countryToLanguages: " + countryToLanguages);


      /*countryToLanguages: {泰国=[泰文], 巴西=[葡萄牙文], =[, 土耳其文, 意大利文, 冰岛文, 印地文, 乌克兰文, 马其顿文, 立陶
宛文, 越南文, 俄文, 爱沙尼亚文, 日文, 瑞典文, 斯洛伐克文, 加泰罗尼亚文, 希伯来文, 爱尔兰文, 泰文, 波兰文, 西班牙文,
 罗马尼亚文, 朝鲜文, 法文, 丹麦文, 马耳他文, 英文, 拉托维亚文(列托), 塞尔维亚文, 保加利亚文, 阿拉伯文, 捷克文, 白俄
罗斯文, 斯洛文尼亚文, 挪威文, 中文, 克罗地亚文, 芬兰文, 希腊文, 匈牙利文, 荷兰文, 葡萄牙文, 德文, 印度尼西亚文, 阿
尔巴尼亚文, 马来文], 塞尔维亚及黑山=[塞尔维亚文], 丹麦=[丹麦文], 塞尔维亚=[塞尔维亚文], 委内瑞拉=[西班牙文], 克罗地
亚=[克罗地亚文], 新西兰=[英文], 阿曼=[阿拉伯文], 约旦=[阿拉伯文], 卢森堡=[德文, 法文], 巴拉圭=[西班牙文], 波斯尼亚
和黑山共和国=[塞尔维亚文], 洪都拉斯=[西班牙文], 伊拉克=[阿拉伯文], 古巴=[西班牙文], 挪威=[挪威文], 萨尔瓦多=[西班牙
文], 黎巴嫩=[阿拉伯文], 阿根廷=[西班牙文], 越南=[越南文], 哥斯达黎加=[西班牙文], 美国=[英文, 西班牙文], 罗马尼亚=[
罗马尼亚文], 卡塔尔=[阿拉伯文], 香港=[中文], 沙特阿拉伯=[阿拉伯文], 秘鲁=[西班牙文], 韩国=[朝鲜文], 尼加拉瓜=[西班
牙文], 巴林=[阿拉伯文], 厄瓜多尔=[西班牙文], 哥伦比亚=[西班牙文], 玻利维亚=[西班牙文], 阿拉伯联合酋长国=[阿拉伯文],
 巴拿马=[西班牙文], 德国=[德文], 马其顿王国=[马其顿文], 立陶宛=[立陶宛文], 波多黎哥=[西班牙文], 以色列=[希伯来文],
墨西哥=[西班牙文], 危地马拉=[西班牙文], 希腊=[希腊文, 德文], 保加利亚=[保加利亚文], 捷克共和国=[捷克文], 英国=[英文
], 印度=[印地文, 英文], 马耳他=[马耳他文, 英文], 匈牙利=[匈牙利文], 塞浦路斯=[希腊文], 波兰=[波兰文], 瑞士=[意大利
文, 德文, 法文], 奥地利=[德文], 法国=[法文], 意大利=[意大利文], 俄罗斯=[俄文], 白俄罗斯=[白俄罗斯文], 荷兰=[荷兰文]
, 台湾地区=[中文], 斯洛文尼亚=[斯洛文尼亚文], 黑山=[塞尔维亚文], 土耳其=[土耳其文], 芬兰=[芬兰文], 瑞典=[瑞典文],
摩洛哥=[阿拉伯文], 西班牙=[加泰罗尼亚文, 西班牙文], 苏丹=[阿拉伯文], 斯洛伐克=[斯洛伐克文], 阿尔及利亚=[阿拉伯文],
比利时=[荷兰文, 法文], 冰岛=[冰岛文], 乌克兰=[乌克兰文], 乌拉圭=[西班牙文], 智利=[西班牙文], 突尼斯=[阿拉伯文], 也
门=[阿拉伯文], 加拿大=[英文, 法文], 日本=[日文], 南非=[英文], 爱沙尼亚=[爱沙尼亚文], 埃及=[阿拉伯文], 利比亚=[阿拉
伯文], 叙利亚=[阿拉伯文], 拉脱维亚=[拉托维亚文(列托)], 马来西亚=[马来文], 多米尼加共和国=[西班牙文], 爱尔兰=[爱尔兰
文, 英文], 葡萄牙=[葡萄牙文], 中国=[中文], 阿尔巴尼亚=[阿尔巴尼亚文], 新加坡=[中文, 英文], 澳大利亚=[英文], 印度尼
西亚=[印度尼西亚文], 科威特=[阿拉伯文], 菲律宾=[英文]}*/

      cities = readCities("cities.txt");
      Map<String, IntSummaryStatistics> stateToCityPopulationSummary = cities
            .collect(groupingBy(City::getState,
                  summarizingInt(City::getPopulation)));
      System.out.println(stateToCityPopulationSummary.get("NY"));
//      IntSummaryStatistics{count=14, sum=9733274, min=49722, average=695233.857143, max=8336697}

      cities = readCities("cities.txt");
      Map<String, String> stateToCityNames = cities.collect(groupingBy(
            City::getState,
            reducing("", City::getName, (s, t) -> s.length() == 0 ? t : s
                  + ", " + t)));

      cities = readCities("cities.txt");
      stateToCityNames = cities.collect(groupingBy(City::getState,
            mapping(City::getName, joining(", "))));
      System.out.println("stateToCityNames: " + stateToCityNames);

      /*stateToCityNames: {DE=Wilmington, HI=Honolulu, TX=Houston, San Antonio, Dallas, Austin, Fort Worth, El Paso, Arling
ton, Corpus Christi, Plano, Laredo, Lubbock, Garland, Irving, Amarillo, Grand Prairie, Brownsville, Pasadena, McKin
ney, Mesquite, McAllen, Killeen, Frisco, Waco, Carrollton, Denton, Midland, Abilene, Beaumont, Round Rock, Odessa,
Wichita Falls, Richardson, Lewisville, Tyler, College Station, Pearland, San Angelo, Allen, League, Sugar Land, Lon
gview, Edinburg, Mission, Bryan, Baytown, Pharr, Temple, Missouri, Flower Mound, Harlingen, North Richland Hills, V
ictoria, Conroe, New Braunfels, Mansfield, Cedar Park, Rowlett, Port Arthur, Euless, Georgetown, Pflugerville, DeSo
to, San Marcos, MA=Boston, Worcester, Springfield, Lowell, Cambridge, New Bedford, Brockton, Quincy, Lynn, Fall Riv
er, Newton, Lawrence, Somerville, Waltham, Haverhill, Malden, Medford, Taunton, Chicopee, Weymouth, Revere, Peabody
, MD=Baltimore, Frederick, Rockville, Gaithersburg, Bowie, ME=Portland, IA=Des Moines, Cedar Rapids, Davenport, Sio
ux, Iowa, Waterloo, Council Bluffs, Ames, West Des Moines, Dubuque, ID=Boise, Nampa, Meridian, Idaho Falls, Pocatel
lo, MI=Detroit, Grand Rapids, Warren, Sterling Heights, Ann Arbor, Lansing, Flint, Dearborn, Livonia, Westland, Tro
y, Farmington Hills, Kalamazoo, Wyoming, Southfield, Rochester Hills, Taylor, Pontiac, St. Clair Shores, Royal Oak,
 Novi, Dearborn Heights, Battle Creek, Saginaw, UT=Salt Lake, West Valley, Provo, West Jordan, Orem, Sandy, Ogden,
St. George, Layton, Taylorsville, South Jordan, Lehi, MN=Minneapolis, St. Paul, Rochester, Duluth, Bloomington, Bro
oklyn Park, Plymouth, St. Cloud, Eagan, Woodbury, Maple Grove, Eden Prairie, Coon Rapids, Burnsville, Blaine, Lakev
ille, Minnetonka, MO=Kansas, St. Louis, Springfield, Independence, Columbia, Billings, Lee's Summit, O'Fallon, St.
Joseph, Missoula, St. Charles, Great Falls, St. Peters, Blue Springs, Florissant, Joplin, IL=Chicago, Aurora, Rockf
ord, Joliet, Naperville, Springfield, Peoria, Elgin, Waukegan, Cicero, Champaign, Bloomington, Arlington Heights, E
vanston, Decatur, Schaumburg, Bolingbrook, Palatine, Skokie, Des Plaines, Orland Park, Tinley Park, Oak Lawn, Berwy
n, Mount Prospect, Normal, Wheaton, Hoffman Estates, Oak Park, IN=Indianapolis, Fort Wayne, Evansville, South Bend,
 Carmel, Bloomington, Fishers, Hammond, Gary, Muncie, Lafayette, Terre Haute, Kokomo, Anderson, Noblesville, Greenw
ood, Elkhart, MS=Jackson, Gulfport, Southaven, AK=Anchorage, AL=Birmingham, Montgomery, Mobile, Huntsville, Tuscalo
osa, Hoover, Dothan, Auburn, Decatur, VA=Virginia Beach, Norfolk, Chesapeake, Richmond, Newport News, Alexandria, H
ampton, Roanoke, Portsmouth, Suffolk, Lynchburg, Harrisonburg, AR=Little Rock, Fort Smith, Fayetteville, Springdale
, Jonesboro, North Little Rock, Conway, Rogers, NC=Charlotte, Raleigh, Greensboro, Durham, Winston-Salem, Fayettevi
lle, Cary, Wilmington, High Point, Greenville, Asheville, Concord, Gastonia, Jacksonville, Chapel Hill, Rocky Mount
, Burlington, ND=Fargo, Bismarck, Grand Forks, RI=Providence, Warwick, Cranston, Pawtucket, NE=Omaha, Lincoln, Bell
evue, AZ=Phoenix, Tucson, Mesa, Chandler, Glendale, Scottsdale, Gilbert, Tempe, Peoria, Surprise, Yuma, Avondale, G
oodyear, Flagstaff, Buckeye, Lake Havasu, NH=Manchester, Nashua, NJ=Newark, Jersey, Paterson, Elizabeth, Clifton, T
renton, Camden, Passaic, Union, Bayonne, East Orange, Vineland, New Brunswick, Hoboken, Perth Amboy, West NY, Plain
field, NM=Albuquerque, Las Cruces, Rio Rancho, Santa Fe, FL=Jacksonville, Miami, Tampa, Orlando, St. Petersburg, Hi
aleah, Tallahassee, Fort Lauderdale, Port St. Lucie, Cape Coral, Pembroke Pines, Hollywood, Miramar, Gainesville, C
oral Springs, Miami Gardens, Clearwater, Palm Bay, Pompano Beach, West Palm Beach, Lakeland, Davie, Miami Beach, Su
nrise, Plantation, Boca Raton, Deltona, Largo, Deerfield Beach, Palm Coast, Melbourne, Boynton Beach, Lauderhill, W
eston, Fort Myers, Kissimmee, Homestead, Tamarac, Delray Beach, Daytona Beach, North Miami, Wellington, North Port,
 Jupiter, Ocala, Port Orange, Margate, Coconut Creek, Sanford, Sarasota, Pensacola, Bradenton, NV=Las Vegas, Hender
son, Reno, North Las Vegas, Sparks, Carson, WA=Seattle, Spokane, Tacoma, Vancouver, Bellevue, Kent, Everett, Renton
, Yakima, Federal Way, Spokane Valley, Bellingham, Kennewick, Auburn, Pasco, Marysville, Lakewood, Redmond, Shoreli
ne, Richland, Kirkland, NY=New York, Buffalo, Rochester, Yonkers, Syracuse, Albany, New Rochelle, Mount Vernon, Sch
enectady, Utica, White Plains, Hempstead, Troy, Niagara Falls, SC=Columbia, Charleston, North Charleston, Mount Ple
asant, Rock Hill, Greenville, SD=Sioux Falls, Rapid, WI=Milwaukee, Madison, Green Bay, Kenosha, Racine, Appleton, W
aukesha, Eau Claire, Oshkosh, Janesville, West Allis, La Crosse, OH=Columbus, Cleveland, Cincinnati, Toledo, Akron,
 Dayton, Parma, Canton, Youngstown, Lorain, Hamilton, Springfield, Kettering, Elyria, Lakewood, GA=Atlanta, Columbu
s, Augusta-Richmond County, Savannah, Athens-Clarke County, Sandy Springs, Roswell, Macon, Johns Creek, Albany, War
ner Robins, Alpharetta, Marietta, Valdosta, Smyrna, OK=Oklahoma, Tulsa, Norman, Broken Arrow, Lawton, Edmond, Moore
, Midwest, CA=Los Angeles, San Diego, San Jose, San Francisco, Fresno, Sacramento, Long Beach, Oakland, Bakersfield
, Anaheim, Santa Ana, Riverside, Stockton, Chula Vista, Irvine, Fremont, San Bernardino, Modesto, Fontana, Oxnard,
Moreno Valley, Huntington Beach, Glendale, Santa Clarita, Garden Grove, Oceanside, Rancho Cucamonga, Santa Rosa, On
tario, Lancaster, Elk Grove, Corona, Palmdale, Salinas, Pomona, Hayward, Escondido, Torrance, Sunnyvale, Orange, Fu
llerton, Pasadena, Thousand Oaks, Visalia, Simi Valley, Concord, Roseville, Victorville, Santa Clara, Vallejo, Berk
eley, El Monte, Downey, Costa Mesa, Inglewood, Carlsbad, San Buenaventura (Ventura), Fairfield, West Covina, Murrie
ta, Richmond, Norwalk, Antioch, Temecula, Burbank, Daly, Rialto, Santa Maria, El Cajon, San Mateo, Clovis, Compton,
 Jurupa Valley, Vista, South Gate, Mission Viejo, Vacaville, Carson, Hesperia, Santa Monica, Westminster, Redding,
Santa Barbara, Chico, Newport Beach, San Leandro, San Marcos, Whittier, Hawthorne, Citrus Heights, Tracy, Alhambra,
 Livermore, Buena Park, Menifee, Hemet, Lakewood, Merced, Chino, Indio, Redwood, Lake Forest, Napa, Tustin, Bellflo
wer, Mountain View, Chino Hills, Baldwin Park, Alameda, Upland, San Ramon, Folsom, Pleasanton, Union, Perris, Mante
ca, Lynwood, Apple Valley, Redlands, Turlock, Milpitas, Redondo Beach, Rancho Cordova, Yorba Linda, Palo Alto, Davi
s, Camarillo, Walnut Creek, Pittsburg, South San Francisco, Yuba, San Clemente, Laguna Niguel, Pico Rivera, Montebe
llo, Lodi, Madera, Santa Cruz, La Habra, Encinitas, Monterey Park, Tulare, Cupertino, Gardena, National, Rocklin, P
etaluma, Huntington Park, San Rafael, La Mesa, Arcadia, Fountain Valley, Diamond Bar, Woodland, Santee, Lake Elsino
re, Porterville, Paramount, Eastvale, Rosemead, Hanford, Highland, Brentwood, Novato, Colton, Cathedral, Delano, Yu
caipa, Watsonville, Placentia, Glendora, Gilroy, Palm Desert, WV=Charleston, WY=Cheyenne, Casper, OR=Portland, Euge
ne, Salem, Gresham, Hillsboro, Beaverton, Bend, Medford, Springfield, Corvallis, Albany, KS=Wichita, Overland Park,
 Kansas, Olathe, Topeka, Lawrence, Shawnee, Manhattan, CO=Denver, Colorado Springs, Aurora, Fort Collins, Lakewood,
 Thornton, Arvada, Westminster, Pueblo, Centennial, Boulder, Greeley, Longmont, Loveland, Grand Junction, Broomfiel
d, Castle Rock, KY=Louisville/Jefferson County, Lexington-Fayette, Bowling Green, Owensboro, CT=Bridgeport, New Hav
en, Stamford, Hartford, Waterbury, Norwalk, Danbury, New Britain, Meriden, Bristol, West Haven, Milford, PA=Philade
lphia, Pittsburgh, Allentown, Erie, Reading, Scranton, Bethlehem, Lancaster, LA=New Orleans, Baton Rouge, Shrevepor
t, Lafayette, Lake Charles, Kenner, Bossier, TN=Memphis, Nashville-Davidson, Knoxville, Chattanooga, Clarksville, M
urfreesboro, Jackson, Franklin, Johnson, Bartlett, Hendersonville, Kingsport, DC=Washington}*/
   }
}
