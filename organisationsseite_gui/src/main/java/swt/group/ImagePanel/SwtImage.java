/*
 * Copyright 2023 di461643.
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
package swt.group.ImagePanel;

import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.request.resource.PackageResourceReference;

/**
 *
 * @author di461643
 */
public class SwtImage extends Panel {

    public SwtImage(String id) {
        super(id);

        PackageResourceReference resourceReference
                = new PackageResourceReference(getClass(), "Softwaretechnik.jpg");
        add(new Image("swtBild", resourceReference));

    }
}
