/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TimetableGeneratorCS_IA;

import TimetableGeneratorCS_IA.data.Subject;

/**
 *
 * @author uczen
 */
public class HelperFunctions {

    /**
     *
     * @param functionName
     * @param message
     */
    public static void generateLog(String functionName, String message) {
        System.out.println("<" + functionName + "> " + message);
    }

    /**
     *
     * @param subjectAr
     * @param checkedSubject
     * @return
     */
    public static Boolean containsSubjectInArray(Subject[] subjectAr, Subject checkedSubject) {
        for (Subject tempSubject : subjectAr) {
            generateLog("containsSubjectInArray", "Checked subject " + tempSubject.getSubjectName() + " against " + checkedSubject.getSubjectName());
            if (tempSubject.getSubjectName().equals(checkedSubject.getSubjectName())) {
                generateLog("containsSubjectInArray", "true");

                return true;
            }
        }
        return false;
    }
}
