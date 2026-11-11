/*
 * Copyright 2013-2026 consulo.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package consulo.localize.ru;

import consulo.annotation.component.ExtensionImpl;
import consulo.configurable.search.SuffixSearchStemmer;

import java.util.Locale;

/**
 * Russian for settings search - the Snowball Russian stemmer, so "пробел" finds "пробелы".
 *
 * @author VISTALL
 * @since 2026-11-11
 */
@ExtensionImpl
public class RussianSearchStemmer extends SuffixSearchStemmer {
    private static final String VOWELS = "аеиоуыэюя";
    /**
     * The letters the endings of the first groups follow.
     */
    private static final String A_YA = "ая";

    private static final String[] PERFECTIVE_GERUND_1 = {"в", "вши", "вшись"};
    private static final String[] PERFECTIVE_GERUND_2 = {"ив", "ивши", "ившись", "ыв", "ывши", "ывшись"};
    private static final String[] REFLEXIVE = {"ся", "сь"};
    private static final String[] ADJECTIVE = {
        "ее", "ие", "ые", "ое", "ими", "ыми", "ей", "ий", "ый", "ой", "ем", "им", "ым", "ом",
        "его", "ого", "ему", "ому", "их", "ых", "ую", "юю", "ая", "яя", "ою", "ею"
    };
    private static final String[] PARTICIPLE_1 = {"ем", "нн", "вш", "ющ", "щ"};
    private static final String[] PARTICIPLE_2 = {"ивш", "ывш", "ующ"};
    private static final String[] VERB_1 = {
        "ла", "на", "ете", "йте", "ли", "й", "л", "ем", "н", "ло", "но", "ет", "ют", "ны", "ть", "ешь", "нно"
    };
    private static final String[] VERB_2 = {
        "ила", "ыла", "ена", "ейте", "уйте", "ите", "или", "ыли", "ей", "уй", "ил", "ыл", "им", "ым", "ен", "ило",
        "ыло", "ено", "ят", "ует", "уют", "ит", "ыт", "ены", "ить", "ыть", "ишь", "ую", "ю"
    };
    private static final String[] NOUN = {
        "а", "ев", "ов", "ие", "ье", "е", "иями", "ями", "ами", "еи", "ии", "и", "ией", "ей", "ой", "ий", "й", "иям",
        "ям", "ием", "ем", "ам", "ом", "о", "у", "ах", "иях", "ях", "ы", "ь", "ию", "ью", "ю", "ия", "ья", "я"
    };
    private static final String[] SUPERLATIVE = {"ейш", "ейше"};
    private static final String[] DERIVATIONAL = {"ост", "ость"};

    @Override
    public Locale getLocale() {
        return Locale.of("ru");
    }

    @Override
    public String stem(String word) {
        StringBuilder stem = new StringBuilder(word.replace('ё', 'е'));
        int rv = rv(stem, VOWELS);
        int r2 = r1(stem, r1(stem, 0, VOWELS), VOWELS);

        step1(stem, rv);

        // step 2: И ->
        if (endsWith(stem, rv, "и")) {
            cut(stem, 1);
        }

        // step 3: a derivational ending in R2
        removeLongestEnding(stem, r2, DERIVATIONAL);

        // step 4: НН -> Н, a superlative ending, or Ь
        if (removeLongestEnding(stem, rv, SUPERLATIVE)) {
            if (endsWith(stem, rv, "нн")) {
                cut(stem, 1);
            }
        }
        else if (endsWith(stem, rv, "нн")) {
            cut(stem, 1);
        }
        else if (endsWith(stem, rv, "ь")) {
            cut(stem, 1);
        }
        return stem.toString();
    }

    /**
     * A perfective gerund - or a reflexive ending, then an adjectival, a verb or a noun ending.
     */
    private static void step1(StringBuilder stem, int rv) {
        if (cut(stem, Math.max(longestEndingAfter(stem, rv, A_YA, PERFECTIVE_GERUND_1), longestEnding(stem, rv, PERFECTIVE_GERUND_2)))) {
            return;
        }

        removeLongestEnding(stem, rv, REFLEXIVE);

        // an adjectival ending - an adjective one, maybe after a participle one
        if (removeLongestEnding(stem, rv, ADJECTIVE)) {
            cut(stem, Math.max(longestEndingAfter(stem, rv, A_YA, PARTICIPLE_1), longestEnding(stem, rv, PARTICIPLE_2)));
            return;
        }

        if (cut(stem, Math.max(longestEndingAfter(stem, rv, A_YA, VERB_1), longestEnding(stem, rv, VERB_2)))) {
            return;
        }

        removeLongestEnding(stem, rv, NOUN);
    }
}
