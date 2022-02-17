import React, { useState } from "react";
import { useLocation } from "react-router-dom";
import { Controlled as CodeMirror } from "react-codemirror2";
import {
  Grid,
  Button,
  Typography,
  makeStyles,
  TextField,
} from "@material-ui/core";
import { jsonOdrlPolicy } from "../components/backend/Submit";
import "codemirror/addon/lint/lint.css";
import "codemirror/lib/codemirror.css";
import "codemirror/theme/material.css";
import "codemirror/mode/javascript/javascript.js";
import { useHistory } from "react-router-dom";
import Input from "../components/controls/Input";

require("codemirror/theme/eclipse.css");

const useStyle = makeStyles((theme) => ({
  root: {
    flexGrow: 1,
    "& .MuiFormControl-root": {
      width: "100%",
    },
    "& .MuiFormControlLabel-root": {
      width: "100%",
    },
    "& .MuiButtonBase-root": {},
  },
  translateBtn: {
    height: "100%",
    width: "100%",
    fontSize: "1.25em",
  },
}));

window.onload = function () {
  var reloading = sessionStorage.getItem("jsonPolicy");
  if (reloading === "null2") {
    sessionStorage.removeItem("reloading");
    window.history.back();
  }
};

export default function ODRLCreator() {
  const history = useHistory();
  var stateLocal = useLocation().state;
  if (stateLocal === undefined) {
    if (typeof stateLocal === "undefined") {
      sessionStorage.removeItem("jsonPolicy");
      history.push({
        pathname: "/",
      });
    }
    stateLocal = {
      jsonPolicy: sessionStorage.getItem("jsonPolicy"),
      dtPolicy: sessionStorage.getItem("dtPolicy"),
    };
  }
  const [policy, setPolicy] = useState(stateLocal.jsonPolicy);
  const [dtPolicy, setDtPolicy] = useState(stateLocal.dtPolicy, null, 2);
  const classes = useStyle();
  const [values, setValues] = useState({ ucAppUrl: "" });
  const [errors, setErrors] = useState({});

  sessionStorage.setItem("jsonPolicy", stateLocal.jsonPolicy);
  sessionStorage.setItem("dtPolicy", stateLocal.dtPolicy);

  const handleInputChange = (e) => {
    setValues({ ...values, [e.target.name]: e.target.value });
  };

  const transfer = () => {
    jsonOdrlPolicy("/policy/JsonOdrlPolicyMYDATA", policy, setDtPolicy);
  };

  const send = () => {
    var query = {};
    query.jsonString = policy;
    query.ucAppUrl = values.AppUrl;
    if (isValidUrl(query.ucAppUrl) == "") {
      setErrors({ ...errors, ["AppUrl"]: "" });
      console.log(query)
      jsonOdrlPolicy("/policy/sendPolicy", query, setDtPolicy);
    } else {
      setErrors({ ...errors, ["AppUrl"]: "Please enter a valid URL" });
    }
  };

  function isValidUrl(string) {
    if (string == "") return "The field should not be empty";

    try {
      new URL(string);
    } catch (_) {
      return "This is not a valid URI";
    }
    return "";
  }
  return (
    <div className={classes.page}>
      <Grid container spacing={1}>
        <Grid item xs={12} md={6}>
          <Grid item xs={12}>
            <Typography variant="h5">Copy your IDS policy here:</Typography>
          </Grid>
          <Grid item xs={12}>
            <CodeMirror
              value={policy}
              onBeforeChange={(editor, data, value) => {
                setPolicy(value);
              }}
              options={{
                lineNumbers: true,
                mode: "application/ld+json",
                styleActiveLine: true,
                line: true,
                lint: true,
                //lineWrapping: true,
                theme: "eclipse",
                matchBrackets: true,
                enableCodeFormatting: true,
                autoFormatOnStart: true,
                autoFormatOnModeChange: true,
              }}
            />
          </Grid>
          <Grid item xs={12}>
            <Grid item xs={3}>
              <Button
                className={classes.translateBtn}
                variant="contained"
                color="secondary"
                id="Transfer"
                onClick={transfer}
              >
                Transfer
              </Button>
            </Grid>
          </Grid>
          <Grid item xs={12}>
            <Input
              name={"AppUrl"}
              value={values["AppUrl"]}
              placeholder=""
              onChange={handleInputChange}
              error={errors["AppUrl"]}
            />
          </Grid>
          <Grid item xs={12}>
            <Grid item xs={3}>
              <Button
                className={classes.translateBtn}
                variant="contained"
                color="secondary"
                id="Send"
                onClick={send}
              >
                Send
              </Button>
            </Grid>
          </Grid>
        </Grid>

        <Grid item xs={12} md={6}>
          <Grid item xs={12}>
            <Typography variant="h5">
              Technology Dependent Policy (MYDATA)
            </Typography>
          </Grid>
          <Grid item xs={12}>
            <CodeMirror
              value={dtPolicy}
              onBeforeChange={(editor, data, value) => {
                setDtPolicy(value);
              }}
              options={{
                lineNumbers: true,
                mode: "application/ld+json",
                styleActiveLine: true,
                line: true,
                lint: true,
                lineWrapping: true,
                theme: "eclipse",
                matchBrackets: true,
                enableCodeFormatting: true,
                autoFormatOnStart: true,
                autoFormatOnModeChange: true,
              }}
            />
          </Grid>
          <Grid item xs={12}>
            <Grid item xs={3}>
              <Button
                className={classes.translateBtn}
                variant="contained"
                color="secondary"
              >
                Export
              </Button>
            </Grid>
          </Grid>
        </Grid>
      </Grid>
    </div>
  );
}
