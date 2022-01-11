import React from "react";
import {  Typography } from "@material-ui/core";
import { makeStyles } from "@material-ui/core";

const useStyles = makeStyles((theme) => ({

}));

export default function PageHeader(props) {
  const { title } = props;
  const classes = useStyles();
  return (
    <div className={classes.pageHeader}>
      
      <div>
        <Typography variant="h5" component="div" id="Title">
        {}{title}
        </Typography>
      </div>
    </div>
  );
}
