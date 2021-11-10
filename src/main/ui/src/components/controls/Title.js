import React from "react";
import { Grid, Typography } from "@material-ui/core";

export default function Title(props) {
  const { label, xs=11, seperator = true } = props;
  if (seperator=== "ff") {
    return (
      <Grid item xs={xs} className="gridSubItemWithLine">
        <Typography variant="subtitle2" className="extraSpaceAfterTitle">{label}</Typography>
      </Grid>
    );
  } else {
    return (
      <Grid item xs={xs} className="gridSubItem">
        <Typography variant="subtitle2" className="extraSpaceAfterTitle">{label}</Typography>
      </Grid>
    );
  }
}
