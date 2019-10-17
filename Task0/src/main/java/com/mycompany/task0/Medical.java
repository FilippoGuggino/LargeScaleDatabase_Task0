/*
 * Copyright (C) 2019 baccio
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.mycompany.task0;

class Medical {
    private String docName;
    private String patName;
    private String date;

    /**
     *   Constructor which takes and initializes all the attributes of the class.
     * 
     *   @param docName  name of the doctor of that particular medical
     *   @param patName  name of the patient of that particular medical
     *   @param date     date in which that particular medical will be held.
     */
    public Medical(String docName,
                   String patName,
                   String date){
        this.docName = docName;
        this.patName = patName;
        this.date = date;
    }

    /**
     *   Returns Medical data as a String formatted as:
     *   <code>Doctor name: "docName" Patient name: "patName" Medical Date: "date"</code>
     * 
     *   @return     String containing Medical data formatted as follows:
     *              <code>Doctor name: "docName" Patient name: "patName" Medical Date: "date"</code>
    */
    @Override
    public String toString(){
        return "Doctor name: " + docName + " Patient name: " + patName + " Medical Date: " + date;
    }
}
