import React from 'react'
import DeleteIcon from "@material-ui/icons/Delete";

import { Grid } from "@material-ui/core";
import IconButton from '@material-ui/core/IconButton';

export default function Remove(props) {
    const { onClick } = props;
    return (
        <Grid item xs={1}>
          <IconButton color="secondary" onClick={onClick}>
            <DeleteIcon />
          </IconButton>
        </Grid>
      );
}