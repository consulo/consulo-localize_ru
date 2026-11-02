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
package consulo.languagetool.spelling.ru;

import consulo.annotation.component.ExtensionImpl;
import consulo.languagetool.spelling.LanguageToolLanguageProvider;
import org.languagetool.Language;
import org.languagetool.language.ru.Russian;

import java.util.List;
import java.util.Locale;
import java.util.Set;

/**
 * @author VISTALL
 * @since 2026-03-28
 */
@ExtensionImpl
public class RussianLanguageToolProvider implements LanguageToolLanguageProvider {

    @Override
    public List<Language> getLanguages() {
        return List.of(LanguageToolLanguageProvider.getLanguage(Russian.class));
    }

    @Override
    public Set<Locale> getLocales() {
        return Set.of(Locale.of("ru", "RU"));
    }
}
