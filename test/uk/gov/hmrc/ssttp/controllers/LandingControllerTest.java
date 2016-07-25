/*
 * Copyright 2016 HM Revenue & Customs
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package uk.gov.hmrc.ssttp.controllers;

import org.junit.Test;
import play.twirl.api.Content;

import static junit.framework.TestCase.fail;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static play.test.Helpers.contentAsString;
import static play.test.Helpers.fakeApplication;
import static play.test.Helpers.running;

public class LandingControllerTest {

    @Test
    public void renderTemplate() {
        running(fakeApplication(), () -> {
           Content html = views.html.landing.render("Self Service Time To Pay");
            assertThat(html.contentType(), is("text/html"));
            assertThat(contentAsString(html), containsString("You can arrange to pay your tax in instalments in some situations"));
        });
   }
}
