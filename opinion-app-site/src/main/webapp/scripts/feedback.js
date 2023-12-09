$(document).ready(function() {

    function showFeedback() {

        var v = null;
        var feedbackMessage = $("<div>" +
            "<div style=\"padding: 0 0 10px 0\">Help us make our website better for you.</div>" +
            "<div class=\"row\" style=\"height: 72px;\">" +
                "<div class=\"cell\">" +
                    "<div><textarea id=\"textarea_feedback_message\" name=\"textarea_feedback_message\" placeholder=\"Please tell us about your experience.\" autocomplete=\"off\" style=\"width: 314px; height: 64px;\"></textarea></div>" +
                    "<div><label id=\"status_feedback_message\"></label></div>" +
                "</div>" +
            "</div>" +
            "<div style=\"padding: 0 0 5px 0; clear: both; font-weight: bold;\">Would you like to be contacted regarding your feedback?</div>" +
            "<div class=\"row\">" +
                "<select id=\"select_feedback\"><option value=\"0\">No - don't contact me. I just wanted to tell you something.</option><option value=\"1\">Yes - please contact me at the email address</option></select>" +
            "</div>" +
            "<div style=\"display: none; clear: both\" id=\"container_feedback_email\">" +
                "<div style=\"padding: 0 0 5px 0; font-weight: bold;\">Email address:</div>" +
                "<div class=\"row\">" +
                    "<div class=\"cell\">" +
                        "<div><input type=\"text\" id=\"text_feedback_email\" name=\"text_feedback_email\" autocomplete=\"off\" style=\"width: 200px;\" /></div>" +
                        "<div><label id=\"status_feedback_email\"></label></div>" +
                    "</div>" +
                "</div>" +
            "</div>" +
        "</div>");

        var I = feedbackMessage.find('#textarea_feedback_message');
        var B = feedbackMessage.find('#status_feedback_message');

        var E = feedbackMessage.find('#text_feedback_email');
        var R = feedbackMessage.find('#status_feedback_email');
        var selectFeedback = feedbackMessage.find('#select_feedback');
        var containerFeedbackEmail = feedbackMessage.find('#container_feedback_email');

        var modal = new lightFace({
            title : "Feedback",
            message : feedbackMessage,
            actions : [
                {
                    label : "Submit",
                    fire : function() {
                        // check validation
                        v.validate();

                    },
                    color: "blue"
                },
                {
                    label : "Cancel",
                    fire : function() {
                        modal.close();
                    },
                    color: "white"
                }
            ],
            overlayAll: true,
            complete : function() {

                I.focus();

                selectFeedback
                    .unbind("keyup change")
                    .bind("keyup change", function() {
                        if($(this).val() != 0) {
                            containerFeedbackEmail.show();
                        } else {
                            containerFeedbackEmail.hide();
                        }
                    });

                v = new validator({
                    elements : [
                        {
                            element : I,
                            status : B,
                            rules : [
                                { method : 'required', message : 'This field is required.' }
                            ]
                        },
                        {
                            element : E,
                            status : R,
                            rules : [
                                { method : 'required', message : 'This field is required.' },
                                { method : 'email', message : 'Please enter a valid email address.' }
                            ],
                            validate : function() {
                                return (selectFeedback.val() != 0);
                            }
                        }
                    ],
                    submitElement : null,
                    messages : null,
                    accept : function () {

                        var obj = {
                            giveUsYourFeedback : {
                                title : "Feedback",
                                email : (selectFeedback.val() != 0 ? E.val() : undefined),
                                description : $.removeHTMLTags(I.val()).replace(/\r/g, "")
                            }
                        };

                        $.getJSON(servletUrl, { rq : JSON.stringify(obj), timestamp: $.getTimestamp() }, function(data) {
                            if(data.giveUsYourFeedback.error != undefined) {
                                alert(data.giveUsYourFeedback.errorDescription);
                            } else {
                                modal.close();
                            }
                        });

                    },
                    error : function() {

                    }
                });

            }
        });

    }

    $("<a class=\"feedback\">Website Feedback</a>").click(function() {
        showFeedback();
    }).appendTo('body');

});