import React from "react";
import { TextField, Grid } from "@material-ui/core";
import {handleInputChange} from "../controls/Utils"

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
