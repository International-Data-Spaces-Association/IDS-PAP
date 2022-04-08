import React from "react";
import { TextField, Grid } from "@material-ui/core";
import { handleInputChange } from "../controls/Utils";

export default function Input(props) {
  const {
    name,
    label,
    placeholder,
    valueHook,
    overrideValue = null,
    overrideOnChange = null,
    errors = null,
    xs = 11,
    sm = 11,
    md = 11,
  } = props;
  const values = valueHook[0];
  var value = values[name];
  var error = "";
  var onChange = handleInputChange;
  if (value === undefined) {
    value = "";
  }
  if (overrideValue !== null) {
    value= overrideValue;
  }
  if (errors !== null && errors[name] !== undefined) {
    error = errors[name];
  }
  if (overrideOnChange !== null) {
    onChange = overrideOnChange;
  }

  return (
    <Grid item xs={xs} sm={sm} md={md}>
      <TextField
        name={name}
        label={label}
        placeholder={placeholder}
        value={value}
        variant="outlined"
        InputLabelProps={{ shrink: true }}
        fullWidth
        onChange={(e) => {
          onChange(e, valueHook);
        }}
        {...(error && { error: true, helperText: error })}
      />
    </Grid>
  );
}
