package com.example.util;

import org.passay.CharacterRule;
import org.passay.EnglishCharacterData;

import java.util.Arrays;
import java.util.List;

/**
 * Created by clint on 9/2/16.
 */
public class Constant {

    public static final String USER_ROLE = "user";
    public static final int PASSWORD_UPPERCASE_MAX = 2;
    public static final int PASSWORD_NUMBERS_MAX = 2;
    public static int PASSWORD_LENGTH = 8;
    public static final String USERNAME = "username";
    public static final String API_KEY = "api_key";
    public static final long IAT = System.currentTimeMillis() / 1000l; // issued at claim
    public static final long EXP = 60L; // expires claim. In this case the token expires in 60 seconds

    public static final List<CharacterRule> PASSWORD_RULES = Arrays.asList(
            // at least one upper-case character
            new CharacterRule(EnglishCharacterData.UpperCase, Constant.PASSWORD_UPPERCASE_MAX),
            // at least one digit character
            new CharacterRule(EnglishCharacterData.Digit, Constant.PASSWORD_NUMBERS_MAX));

    public static final String NOT_NULL_MESSAGE = "Verplicht";
    public static final String NOT_DATE_MESSAGE = "Date required";
    public static final String NOT_DECIMAL_MESSAGE = "Decimal required";
    public static final String SIZE_MESSAGE = "Not a valid field size";
    public static final String ONLY_BEFORE_TODAY = "Only past dates";
    public static final String QLIBRARY_ADMIN = "Qlibrary Admin";
    public static final String NO_REPLY = "noreply@qualogy.sr";
    public static final String SMTP_SERVER = "172.19.1.226";
    public static final String YEAR_BETWEEN_RANGE_REQUIRED = "Year required between 1000 and 9999";
    public static final String EMPTY_STRING = "";

    public static final String PASSWORD_MATCH_MESSAGE  = "Password doesn't match";
    public static final String EMAIL_MESSAGE = "Onjuiste emailformaat";

    public static final String STATUS_RESERVED = "RESERVED";
    public static final String STATUS_CHECKED_OUT = "CHECKED OUT";

    public static final String OM_EMAIL_ADRESS = "rkaffe@qualogy.sr";
    public static final String DBA_EMAIL_ADRESS = "mmichelly@qualogy.sr";
    public static final String USER_EMAIL_ADRESS = "ndarsan@qualogy.sr";
    public static final String PDF_MIME_TYPE = "application/pdf";

    public static final String REPORTS_DATE_FORMAT = "dd-MM-yyyy";

    public static final String MAX_MESSAGE_255 = "Only 255 characters allowed";
    public static final String MAX_MESSAGE_500 = "Only 500 characters allowed";
    public static final String MAX_MESSAGE_10 = "Only 10 characters allowed";

    public static final String REQUEST_VARIABLE = "requestVariable";
    public static final String HOOFD_TAAK = "hoofdTaak";
    public static final String PROCESS_FINABANK_LOAN = "process_finabank_loan";
    public static final String PROCESS_FINABANK_LOAN_GENERIC = "process_finabank_generic";
    public static final String PROCESS_FINABANK_COMM_HYP = "process_finabank_comm_hyp";
    public static final String RM_BM = "RM-BM";
    public static final String GS_DIR = "GS-DIR";
    public static final String GS_DIR_MAN = "GS-DIR-MAN";
    public static final String GS_KREDIETCOMISSIE = "GS-KREDIETCOMISSIE";
    public static final String GS_RISICOCOMITEE = "GS-RISICOCOMITEE";


    public static final String NOT_VALID_MONEY = "Geen valide bedrag";

    public static final String NOT_VALID_AMOUNT = "Geen valide aantal";

    public static final String NOT_VALID_LOOPTIJD = "Rente moet tussen 0 en 99 zijn";
    public static final String RO_ROLE = "retail-officer";

    public static final String RETAIL_OFFICER = "retail-officer";
    public static final String SUPERVISOR = "supervisor";
    public static final String BRANCHMANAGER = "branch-manager";
    public static final String GOEDKEURINGS_STRUCTUUR = "goedkeurings-structuur";
    public static final String LEGAL = "legal";
    public static final String CAD = "cad";

    public static final String TASK_KEY_CLIENTGENERALINFO = "clientGeneralInfoTask";
    public static final String TASK_KEY_CLIENTPRODUCTINFO = "clientProductRelevantInfoTask";
    public static final String TASK_KEY_CLIENTFEEDBACK = "clientFeedbackTask";
    public static final String TASK_KEY_ANALYSES = "analysesTask";

    public static final String FINABANK_CODE = "FNB";
    public static final String VEREISTE_REKENING_MUNTEENHEID = "USD";
    public static final Long STATUS_DONE = Long.valueOf(1);
    public static final Long STATUS_PENDING = Long.valueOf(2);

    public static final String AANVRAAG_STATUS_DONE = "DONE";


    public static final String[] COMMENTAAR_NAW_SECTIES= new String[]{"naw.personalia",
            "naw.correspondentieadres", "naw.cbbadres", "naw.pep", "naw.insider", "naw.werkgever",
            "naw.inkomenah", "naw.overigekosten", "naw.bankrekening", "naw.lening", "naw.verbondenpartij", };
}
