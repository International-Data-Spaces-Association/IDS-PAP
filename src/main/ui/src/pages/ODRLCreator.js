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
import {jsonOdrlPolicy} from "../components/backend/Submit";
import "codemirror/addon/lint/lint.css";
import "codemirror/lib/codemirror.css";
import "codemirror/theme/material.css";
import 'codemirror/mode/javascript/javascript.js';

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

window.onload = function() {
  var reloading = sessionStorage.getItem("reloading");
  if (reloading) {
      sessionStorage.removeItem("reloading");
      window.history.back();
  }
}

export default function ODRLCreator() {
  const [policy, setPolicy] = useState(useLocation().state.jsonPolicy);
  const [dtPolicy, setDtPolicy] = useState(useLocation().state.dtPolicy, null, 2);
  const classes = useStyle();

  sessionStorage.setItem("reloading", "true");

  const transfer = () =>{
    jsonOdrlPolicy("/policy/JsonOdrlPolicyMYDATA",policy ,setDtPolicy)
  }
  return (
    <div className={classes.page}>
      <Grid container spacing={1}>
        <Grid item xs={12} md={6}>
          <Grid item xs={12}>
            <Typography variant="h5">Copy your ODRL policy here:</Typography>
          </Grid>
          <Grid item xs={12}>
            <CodeMirror
              value={policy}
              onBeforeChange={(editor, data, value) => {
                setPolicy(value);
              }}
              options={{
                lineNumbers: true,
                mode: 'application/ld+json',
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
            <TextField
              name="AppUrl"
              variant="outlined"
              style={{ width: "100%" }}
            />
          </Grid>
          <Grid item xs={12}>
            <Grid item xs={3}>
              <Button
                className={classes.translateBtn}
                variant="contained"
                color="secondary"
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
                mode: 'application/ld+json',
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
