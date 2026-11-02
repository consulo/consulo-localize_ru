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

import org.jspecify.annotations.NullMarked;

/**
 * @author VISTALL
 * @since 2026-03-28
 */
@NullMarked
module consulo.language.tool.spelling.ru {
    requires consulo.annotation;
    requires consulo.component.api;
    requires consulo.language.tool.spelling;  // transitive languagetool.core pulled in here
    requires org.languagetool.language.ru;

    exports consulo.languagetool.spelling.ru;
}
