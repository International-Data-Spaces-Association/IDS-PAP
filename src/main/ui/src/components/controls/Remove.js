/**
 * @file This file contains the remove component 
 * @author Tom Kollmer 
 */

import React from "react";
import DeleteIcon from "@material-ui/icons/Delete";
import { useStyle } from "../Style";

import { Grid } from "@material-ui/core";
import IconButton from "@material-ui/core/IconButton";

/**
 * remove component 
 * @component
 * @param {func} onClick is called when the button is pressed
 * @returns component
 */
export default function Remove(props) {
  const { onClick } = props;
  const classes = useStyle();
  return (
    <Grid item xs={1} container justifyContent="center">
      <IconButton color="secondary" onClick={onClick} className={classes.formBtn}>
        <DeleteIcon />
      </IconButton>
    </Grid>
  );
}
