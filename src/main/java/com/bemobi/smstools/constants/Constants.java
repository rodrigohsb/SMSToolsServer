package com.bemobi.smstools.constants;

/**
 * Created by rodrigo.bacellar on 03/07/2015.
 */
public class Constants {

    public static final String UTF8 = "UTF-8";

    public static final String PARAM_TO = "to";
    public static final String PRIORITY = "priority";
    public static final String PARAM_PLAINTEXT_PAYLOAD_PREFIX = "data.";
    public static final String RESPONSE_PLAINTEXT_MESSAGE_ID = "id";
    public static final String RESPONSE_PLAINTEXT_CANONICAL_REG_ID = "registration_id";
    public static final String RESPONSE_PLAINTEXT_ERROR = "Error";

    public static final String HEADER_CONTENT_TYPE = "Content-Type";
    public static final String HEADER_PROJECT_ID = "project_id";
    public static final String HEADER_AUTHORIZATION = "Authorization";

    public static final String CONTENT_TYPE_FORM_ENCODED = "application/x-www-form-urlencoded";
    public static final String CONTENT_TYPE_JSON = "application/json";

    public static final String API_KEY = "AIzaSyDKi7il3_wS-cCIGQ8G1vuJN5cu9kfRWk4";


    public static final String LA = "la";
    public static final String TEXT = "text";

    public static final String RESTRICTED_PACKAGE_NAME = "com.bemobi.app.smstools";

    public static final String COLLAPSE_KEY = "collapseKey";
    public static final boolean DELAY_WHILE_IDLE = true;
    public static final boolean DRY_RUN = true;
    public static final int TTL = 108;


    public static final String GCM_SEND_ENDPOINT = "https://android.googleapis.com/gcm/send";

    /**
     * HTTP parameter for registration id.
     */
    public static final String PARAM_REGISTRATION_ID = "registration_id";

    /**
     * HTTP parameter for collapse key.
     */
    public static final String PARAM_COLLAPSE_KEY = "collapse_key";

    /**
     * HTTP parameter for delaying the message delivery if the device is idle.
     */
    public static final String PARAM_DELAY_WHILE_IDLE = "delay_while_idle";

    /**
     * HTTP parameter for telling gcm to validate the message without actually sending it.
     */
    public static final String PARAM_DRY_RUN = "dry_run";

    /**
     * HTTP parameter for package name that can be used to restrict message delivery by matching
     * against the package name used to generate the registration id.
     */
    public static final String PARAM_RESTRICTED_PACKAGE_NAME = "restricted_package_name";

    /**
     * Prefix to HTTP parameter used to pass key-values in the message payload.
     */
    public static final String PARAM_PAYLOAD_PREFIX = "data.";

    /**
     * Prefix to HTTP parameter used to set the message time-to-live.
     */
    public static final String PARAM_TIME_TO_LIVE = "time_to_live";

    /**
     * Too many messages sent by the sender. Retry after a while.
     */
    public static final String ERROR_QUOTA_EXCEEDED = "QuotaExceeded";

    /**
     * Too many messages sent by the sender to a specific device.
     * Retry after a while.
     */
    public static final String ERROR_DEVICE_QUOTA_EXCEEDED =
            "DeviceQuotaExceeded";

    /**
     * Missing registration_id.
     * Sender should always add the registration_id to the request.
     */
    public static final String ERROR_MISSING_REGISTRATION = "MissingRegistration";

    /**
     * Bad registration_id. Sender should remove this registration_id.
     */
    public static final String ERROR_INVALID_REGISTRATION = "InvalidRegistration";

    /**
     * The sender_id contained in the registration_id does not match the
     * sender_id used to register with the GCM servers.
     */
    public static final String ERROR_MISMATCH_SENDER_ID = "MismatchSenderId";

    /**
     * The user has uninstalled the application or turned off notifications.
     * Sender should stop sending messages to this device and delete the
     * registration_id. The client needs to re-register with the GCM servers to
     * receive notifications again.
     */
    public static final String ERROR_NOT_REGISTERED = "NotRegistered";

    /**
     * The payload of the message is too big, see the limitations.
     * Reduce the size of the message.
     */
    public static final String ERROR_MESSAGE_TOO_BIG = "MessageTooBig";

    /**
     * Collapse key is required. Include collapse key in the request.
     */
    public static final String ERROR_MISSING_COLLAPSE_KEY = "MissingCollapseKey";

    /**
     * A particular message could not be sent because the GCM servers were not
     * available. Used only on JSON requests, as in plain text requests
     * unavailability is indicated by a 503 response.
     */
    public static final String ERROR_UNAVAILABLE = "Unavailable";

    /**
     * A particular message could not be sent because the GCM servers encountered
     * an error. Used only on JSON requests, as in plain text requests internal
     * errors are indicated by a 500 response.
     */
    public static final String ERROR_INTERNAL_SERVER_ERROR =
            "InternalServerError";

    /**
     * Time to Live value passed is less than zero or more than maximum.
     */
    public static final String ERROR_INVALID_TTL= "InvalidTtl";

    /**
     * Token returned by GCM when a message was successfully sent.
     */
    public static final String TOKEN_MESSAGE_ID = "id";

    /**
     * Token returned by GCM when the requested registration id has a canonical
     * value.
     */
    public static final String TOKEN_CANONICAL_REG_ID = "registration_id";

    /**
     * Token returned by GCM when there was an error sending a message.
     */
    public static final String TOKEN_ERROR = "Error";

    /**
     * JSON-only field representing the registration ids.
     */
    public static final String JSON_REGISTRATION_IDS = "registration_ids";

    /**
     * JSON-only field representing the payload data.
     */
    public static final String JSON_PAYLOAD = "data";

    /**
     * JSON-only field representing the number of successful messages.
     */
    public static final String JSON_SUCCESS = "success";

    /**
     * JSON-only field representing the number of failed messages.
     */
    public static final String JSON_FAILURE = "failure";

    /**
     * JSON-only field representing the number of messages with a canonical
     * registration id.
     */
    public static final String JSON_CANONICAL_IDS = "canonical_ids";

    /**
     * JSON-only field representing the id of the multicast request.
     */
    public static final String JSON_MULTICAST_ID = "multicast_id";

    /**
     * JSON-only field representing the result of each individual request.
     */
    public static final String JSON_RESULTS = "results";

    /**
     * JSON-only field representing the error field of an individual request.
     */
    public static final String JSON_ERROR = "error";

    /**
     * JSON-only field sent by GCM when a message was successfully sent.
     */
    public static final String JSON_MESSAGE_ID = "message_id";
}
