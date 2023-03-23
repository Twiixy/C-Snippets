/*
 * Copyright 2023 hvo.
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
package swt.group;

/**
 *
 * @author hvo
 */
public class InfoseiteData {
    public String id;
    public String inhalt;
    
    public InfoseiteData(String id, String inhalt) {
        this.id=id;
        this.inhalt=inhalt;
    }

    @Override
    public String toString() {
        return "InfoseiteData{" + "id=" + id + ", inhalt=" + inhalt + '}';
    }
    
}
