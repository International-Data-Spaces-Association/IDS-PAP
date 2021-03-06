import React from "react";
import { TextField, Grid } from "@material-ui/core";

export default function Date(props) {
  const { name,label, value, error = null, onChange, xs=11, sm=11, md=11} = props;
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
          onChange={onChange}
          {...(error && { error: true, helperText: error })}
        />
      </Grid>
    </>
  );
}
