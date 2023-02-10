import React, { useState } from "react";
import { Grid, Button, Paper, withStyles } from "@material-ui/core";
import Backdrop from "@material-ui/core/Backdrop";
import CircularProgress from "@material-ui/core/CircularProgress";
import "codemirror/addon/lint/lint.css";
import "codemirror/lib/codemirror.css";
import "codemirror/theme/material.css";
import "codemirror/mode/javascript/javascript.js";
import Input from "../components/controls/Input";
import Form from "../components/controls/Form";
import IdentifyPolicy from "../components/controls/IdentifyPolicy";
import { useStyle } from "../components/Style";
import Title from "../components/controls/Title";
import Date from "../components/controls/Date";
import MultiSelectInputField from "../components/controls/MultiselectInputField";
import { sale_rent_list } from "../components/controls/InitialFieldListValues";
import ItemPicker from "../components/controls/ItemPicker";
import LogData from "../components/controls/LogData";
import { OdrlPolicy } from "../components/backend/OdrlPolicy";
import { negotiatePolicyGetUUID, negotiatePolicyGetResponse } from "../components/backend/Submit";

require("codemirror/theme/eclipse.css");

const LimitedBackdrop = withStyles({
  root: {
    position: "absolute",
    zIndex: 1,
  },
})(Backdrop);

export default function NegotiatePolicy() {
  const valueHook = useState(OdrlPolicy);
  const addressHook = useState({ url: "http://localhost:8080/negotiator/v1" });

  const errors = useState({})[0];

  const classes = useStyle();
  const [showBackdrop, setShowBackdrop] = React.useState(false);

  
  function sleep(ms) {
    return new Promise((resolve) => {
      setTimeout(resolve, ms);
    });
  }

  async function handleButtonClick(url) {
    setShowBackdrop(true);
    let uuid = await negotiatePolicyGetUUID(url);
    await sleep(10000)
    let response = await negotiatePolicyGetResponse(url, uuid);
    setShowBackdrop(false);
  };

  return (
    <div className={classes.page}>
      <Form onSubmit={() => handleButtonClick(addressHook[0]["url"])}>
        <Grid container>
          <LimitedBackdrop open={showBackdrop}>
            <CircularProgress size={"4rem"} style={{ color: "#239b7e" }} />
          </LimitedBackdrop>
          <Grid item xs={12}>
            <Paper elevation={3} className={classes.paperWithoutRemoveBtn}>
              <IdentifyPolicy valueHook={valueHook} errors={errors} />

              <Grid container key={"interval"}>
                <Title label="Restrict Time Interval" />
                <Grid container spacing={2}>
                  <Date
                    name="restrictStartTime"
                    label="Start Time*"
                    valueHook={valueHook}
                    errors={errors}
                    sm={11}
                    md={4}
                  />
                  <Date
                    name="restrictEndTime"
                    label="End Time*"
                    valueHook={valueHook}
                    errors={errors}
                    sm={11}
                    md={4}
                  />
                </Grid>
              </Grid>
              <Grid container key={"location"}>
                <Title label="Restrict Location" />
                <MultiSelectInputField
                  name="location"
                  valueHook={valueHook}
                  placeholder="e.g. https://ontologi.es/place/DE"
                  errors={errors}
                />
              </Grid>
              <Grid container key={"payment"}>
                <Title label="Restrict Payment" />
                <Grid container spacing={2} xs={11}>
                  <Input
                    name="price"
                    label="Payment (Euro)*"
                    placeholder="e.g. 10"
                    valueHook={valueHook}
                    errors={errors}
                    sm={11}
                    md={3}
                  />
                  <ItemPicker
                    name="payment"
                    label="For Sale or Rent*"
                    defaultValue=""
                    ItemList={sale_rent_list}
                    valueHook={valueHook}
                    errors={errors}
                    sm={11}
                    md={3}
                  />
                </Grid>
              </Grid>
              <Title label="Log Data Usage" />
              <LogData
                valueHook={valueHook}
                errors={errors}
                xs={12}
                sm={12}
                md={12}
                prefix="postduties_"
              />
              <Grid container key={"url"}>
                <Title label="Server URL" />
                <Grid container spacing={2} xs={11}>
                  <Input
                    label="URL"
                    name={"url"}
                    placeholder="e.g. http://example.com/ids/system/ESN-database"
                    valueHook={addressHook}
                    errors={errors}
                  />
                </Grid>
              </Grid>
            </Paper>
          </Grid>
          <Grid item xs={2}>
            <Button
              variant="contained"
              color="secondary"
              className={classes.saveBtn}
              type="submit"
              id="Save"
            >
              Submit
            </Button>
          </Grid>
        </Grid>
      </Form>
    </div>
  );
}
