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
package swt.group.betrieb;

import java.io.Serializable;

/**
 *
 * @author di461643
 */
public class BetriebData implements Serializable {

    public String id;
    public String personID;
    public String titel;
    public String beschreibung;
    public String kommentar;
    public boolean istAbgeschlossen;

    public BetriebData(String id, String personId, String titel, String beschreibung, String kommentar, boolean istAbgeschlossen) {
        this.id = id;
        this.personID = personId;
        this.titel = titel;
        this.beschreibung = beschreibung;
        this.kommentar = kommentar;
        this.istAbgeschlossen = istAbgeschlossen;
    }

    @Override
    public String toString() {
        return "BetriebData{" + "id=" + id + ", personID=" + personID + ", titel=" + titel + ", beschreibung=" + beschreibung + ", kommentar=" + kommentar + ", istAbgeschlossen=" + istAbgeschlossen + '}';
    }
}
