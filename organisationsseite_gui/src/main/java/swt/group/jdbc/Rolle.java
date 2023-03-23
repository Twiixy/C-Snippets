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
package swt.group.jdbc;

/**
 *
 * @author hvo
 */
public enum Rolle {
    ADMIN(0, "admin"),
    BETREUER(1, "betreuer"),
    AZUBI(2, "azubi"),
    OTHER(-1, "other");

    private final int id;
    private final String name;

    private Rolle(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public static Rolle of(int id) {
        switch (id) {
            case 0:
                return ADMIN;
            case 1:
                return BETREUER;
            case 2:
                return AZUBI;
            default:
                return OTHER;
        }

    }
}
