import React from "react";
import { Grid, Typography } from "@material-ui/core";

export default function Title(props) {
  const { label, subtitle, seperator = true } = props;
  if (seperator) {
    return (
      <Grid item xs={11} className="gridSubItemWithLine">
        <Typography variant="subtitle2" className="extraSpaceAfterTitle">{label}</Typography>
      </Grid>
    );
  } else {
    return (
      <Grid item xs={11} className="gridSubItem">
        <Typography variant="subtitle2" className="extraSpaceAfterTitle">{label}</Typography>
      </Grid>
    );
  }
}
