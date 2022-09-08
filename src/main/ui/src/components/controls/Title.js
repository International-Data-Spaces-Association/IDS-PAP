/**
 * @file This contains the title component 
 * @author Tom Kollmer 
 */

import React from "react";
import { Grid, Typography } from "@material-ui/core";

/**
 * Components for the post duty data component
 * @component
 * @param {string} label that should be used for the component
 * @param {number} xs size of the component at small screens
 * @param {boolean} separator If true a line is added to separate the component from others
 * @returns component
 */
export default function Title(props) {
  const { label, xs=11, separator = true } = props;
  if (separator) {
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
