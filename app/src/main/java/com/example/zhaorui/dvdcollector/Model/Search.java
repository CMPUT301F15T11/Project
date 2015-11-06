/*
 *
 *University of Alberta CMPUT 301 Group: CMPUT301F15T11
 *Copyright {2015} {Dingkai Liang, Zhaorui Chen, Jiaxuan Yue, Xi Zhang, Qingdai Du, Wei Song}
 *
 *Licensed under the Apache License, Version 2.0 (the "License");
 *
 *you may not use this file except in compliance with the License.
 *You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 *Unless required by applicable law or agreed to in writing,software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied.
 * See the License for the specific language governing permissions
 * and limitations under the License.
*/
package com.example.zhaorui.dvdcollector.Model;
/**
 * <p>
 * The <code>Search</code> class allowed users to search an inventory by name, category and sharable.
 * <p>
 *
 * @author  Dingkai Liang
 * @version 01/11/15
 */
public class Search {
    /**
     *Initialize a user.
     */
    private User user;
    /**
     *General constructor.
     */
    public Search(User user) {
        this.user = user;
    }
    /**
     *Search inventory by index.
     * @param index, an int variable
     */
    public Inventory searchInventory(int index){return null;}
    /**
     *Search inventory by category..
     * @param category, a string variable.
     */
    public Inventory searchByCategory(String category){return null;}
    /**
     *Search inventory by text.
     * @param text, a string variable.
     */
    public Inventory searchByText(String text){return null;}
    /**
     *Search inventory by sharable or not.
     *
     */
    public Inventory searchShared(){return null;}
}