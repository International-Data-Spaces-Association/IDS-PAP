/**
 * @file This file contains the date component
 * @author Tom Kollmer 
 */

import React from "react";
import { TextField, Grid } from "@material-ui/core";
import {handleInputChange} from "../controls/Utils"

/**
 * Components for anonymize pages
 * @component
 * @param {string} name of the component
 * @param {string} label of the component
 * @param {object} valueHook access to the user input
 * @param {object} errors contains all error messages
 * @param {number} xs size of the component at small screens
 * @param {number} sm size of the component at medium screens
 * @param {number} md size of the component at large screens
 * @returns component
 */
export default function Date(props) {
  const { name,label, valueHook, errors = null, xs=11, sm=11, md=11} = props;
  const values = valueHook[0];
  var value = values[name]
  var error = ""
  if (value === undefined) {
    value = ""
  }
  if (errors !== null && errors[name] !== undefined){
    error = errors[name]
  }
  return (
    <>
      <Grid item xs={xs} sm={sm} md={md}>
        <TextField
          name={name}
          value={value}
          label={label}
          type="datetime-local"
          variant="outlined"
          InputLabelProps={{ shrink: true }}
          onChange={e =>{handleInputChange(e, valueHook)}}
          {...(error && { error: true, helperText: error })}
        />
      </Grid>
    </>
  );
}
