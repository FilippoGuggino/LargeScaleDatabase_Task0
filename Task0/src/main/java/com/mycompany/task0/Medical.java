package com.mycompany.task0;

class Medical {
    private String docName;
    private String patName;
    private String date;

    /*
        Constructor which takes and initialize all the attributes of the class.

        @param docName  name of the doctor of that particular medical
        @param patName  name of the patient of that particular medical
        @param date     date in which that particular medical will be held.
     */
    public Medical(String docName,
                   String patName,
                   String date){
        this.docName = docName;
        this.patName = patName;
        this.date = date;
    }

    /*
        Return Medical data as a String formatted as:
        <code>Doctor name: "docName" Patient name: "patName" Medical Date: "date"</code>

        @return     String containing Medical data formatted as follows:
                    <code>Doctor name: "docName" Patient name: "patName" Medical Date: "date"</code>
    */
    @Override
    public String toString(){
        return "Doctor name: " + docName + "Patient name: " + patName + "Medical Date: " + date;
    }
}
