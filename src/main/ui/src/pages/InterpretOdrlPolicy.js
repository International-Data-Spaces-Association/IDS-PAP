import React, { useState } from "react";
import { Controlled as CodeMirror } from "react-codemirror2";
import { Grid, Button, Typography, makeStyles } from "@material-ui/core";
import {jsonOdrlPolicy} from "../components/backend/Submit";
import "codemirror/addon/lint/lint.css";
import "codemirror/lib/codemirror.css";
import "codemirror/theme/material.css";
import 'codemirror/mode/javascript/javascript.js';
require("codemirror/theme/eclipse.css");

const useStyle = makeStyles((theme) => ({
  root: {
    flexGrow: 1,
    marginTop: "3em",
    "& .MuiFormControl-root": {
      width: "100%",
    },
    "& .MuiFormControlLabel-root": {
      width: "100%",
    },
    "& .MuiButtonBase-root": {},
  },
  page: {
    marginLeft: "2em",
    marginRight: "2em",
  },
  translateBtn: {
    height: "100%",
    width: "100%",
    fontSize: "1.25em",
  },
}));


const defaultTranslation =
  "The provider is an IDS party that is issuing the rule. Here the provider is the my-party party. This party is either the Data Owner or the Data Provider of the specified data asset in the IDS context.In this Policy OFFER example, the permission rule allows the Data Consumer to use the target asset.The identifier of this policy and the target asset are http://example.com/policy/sample and http://example.com/ids-data/sample, respectively.";
const defaultPolicy ='{\n \t "@context": {\n \t\t"ids":"https://w3id.org/idsa/core/",\n \t \t"idsc" : "https://w3id.org/idsa/code/"\n \t },    \n \t "@type": "ids:ContractOffer",\n \t "@id": "https://w3id.org/idsa/autogen/contract/sample",\n \t "profile": "http://example.com/ids-profile", \n \t "ids:provider": "http://example.com/party/my-party", \n \t "ids:permission": [{ \n \t "ids:target": {\n \t\t "@id":"http://example.com/ids-data/sample"\n \t }, \n \t "ids:action": [{\n \t\t "@id":"idsc:USE"\n \t \t}]\n \t }] \n  }'
export default function InterpretOdrlPolicy() {
  const [policy, setPolicy] = useState(defaultPolicy);
  const [dtPolicy, setDtPolicy] = useState(defaultTranslation);
  const classes = useStyle();

  const transfer = () =>{
    jsonOdrlPolicy("/policy/JsonOdrlPolicyMYDATA", policy, setDtPolicy)
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
        </Grid>
        <Grid item xs={12} md={6}>
          <Grid item xs={12}>
            <Typography variant="h5">Translation</Typography>
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
        </Grid>
        <Grid item xs={2}>
          <Button
            className={classes.translateBtn}
            variant="contained"
            color="secondary"
            onClick={transfer}
          >
            Translate
          </Button>
        </Grid>
      </Grid>
    </div>
  );
}
