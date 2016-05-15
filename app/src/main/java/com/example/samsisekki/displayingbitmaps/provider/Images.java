/*
 * Copyright (C) 2012 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.samsisekki.displayingbitmaps.provider;

import java.util.ArrayList;
import java.util.List;

/**
 * Some simple test data to use for this sample app.
 */
public class Images {

    /**
     * This are PicasaWeb URLs and could potentially change. Ideally the PicasaWeb API should be
     * used to fetch the URLs.
     *
     * Credit to Romain Guy for the photos:
     * http://www.curious-creature.org/
     * https://plus.google.com/109538161516040592207/about
     * http://www.flickr.com/photos/romainguy
     */
    public final static String IP = "203.246.82.97:48780";

    public final static String[] menu = new String[] {
            "chicken","yangnyumchicken","pachicken","snowchicken","ganjangchicken","crispychicken",
            "roastchiken","onionchicken",
            "jogbal","buljogbal","nangchejogbal","bossam",
            "kimchizzigea","ugujizzigea","denjangzzigea","fishsoondubuzzigea","fishzzigea","gamjazzigea",
            "chanchikimchizzigea",
            "ahrtangzzigea","boodeazzigea",
            "goolbab","mushroomresoto","namoolrice","gamjaburger","fishgratang","golbengrice","redwineyangsik"
            ,"ramennoodle"
    };
    public final static String[] menu2 = new String[] {
            "후라이드치킨","양념치킨","파닭","스노우치킨","간장치킨","크리스피치킨", "구운치킨","어니언치킨"
            ,"족발","불족발","냉채족발","보삼",
            "김치찌개","우거지찌개","된장찌개","해물순두부찌개","해물탕","감자탕","참치김치찌개","알탕","부대찌개",
            "굴밥","버섯리조토","나물비빔밥","감자햄버거","해물그라탕","골뱅이덮밥","레드와인스테이크"
            ,"라면"
    };

    public final static String[] classname = new String[] {
            "chicken","jogbal","pizza","rice","noodle","yangsik",
            "zzigea","tuigim","resoto","gratang","burger"
    };
    public final static String[] classimage = new String[]{
            "http://203.246.82.97:48780/img/2.jpg",
            "http://203.246.82.97:48780/img/jookbal.jpg",
            "http://203.246.82.97:48780/img/9.jpg",
            "http://203.246.82.97:48780/img/6.jpg",
            "http://203.246.82.97:48780/img/12.jpg",
            "http://203.246.82.97:48780/img/7.jpg",
            "http://203.246.82.97:48780/img/1.jpg",
            "http://203.246.82.97:48780/img/tuigim.jpg",
            "http://203.246.82.97:48780/img/mushroomresoto.jpg",
            "http://203.246.82.97:48780/img/gratang.jpg",
            "http://203.246.82.97:48780/img/burger.jpg"
    };
    public final static String[] imageThumbUrls = new String[] {
            "http://203.246.82.97:48780/img/chicken.jpg",
            "http://203.246.82.97:48780/img/yangnyumchicken.jpg",
            "http://203.246.82.97:48780/img/pachicken.jpg",
            "http://203.246.82.97:48780/img/snowchicken.jpg",
            "http://203.246.82.97:48780/img/ganjangchicken.jpg",
            "http://203.246.82.97:48780/img/crispychicken.jpg",
            "http://203.246.82.97:48780/img/roastchicken.jpg",
            "http://203.246.82.97:48780/img/onionchicken.jpg",
            "http://203.246.82.97:48780/img/jookbal.jpg",
            "http://203.246.82.97:48780/img/buljookbal.jpg",
            "http://203.246.82.97:48780/img/nangchejookbal.jpg",
            "http://203.246.82.97:48780/img/bossam.jpg",
            "http://203.246.82.97:48780/img/kimchi.jpg",
            "http://203.246.82.97:48780/img/ugujizzigea.jpg",
            "http://203.246.82.97:48780/img/denjangzzigea.jpg",
            "http://203.246.82.97:48780/img/fishsoondubuzzigea.jpg",
            "http://203.246.82.97:48780/img/fishzzigea.jpg",
            "http://203.246.82.97:48780/img/gamjatang.jpg",
            "http://203.246.82.97:48780/img/chanchikimchizzigea.jpg",
            "http://203.246.82.97:48780/img/ahrtang.jpg",
            "http://203.246.82.97:48780/img/boodeazzigea.jpg",
            "http://203.246.82.97:48780/img/goolbab.jpg",
            "http://203.246.82.97:48780/img/mushroomresoto.jpg",
            "http://203.246.82.97:48780/img/namoolbibimbab.jpg",
            "http://203.246.82.97:48780/img/gamjaburger.jpg",
            "http://203.246.82.97:48780/img/fishgratang.jpg",
            "http://203.246.82.97:48780/img/golbengeidupbab.jpg",
            "http://203.246.82.97:48780/img/redwinestake.jpg",
            "http://203.246.82.97:48780/img/ramen.jpg"
    };
}
