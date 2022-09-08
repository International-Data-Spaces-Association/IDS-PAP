/**
 * @file This file contains the page header component
 * @author Tom Kollmer 
 */

import React from "react";
import {  Typography } from "@material-ui/core";
import { makeStyles } from "@material-ui/core";

const useStyles = makeStyles((theme) => ({

}));

/**
 * Title component that is used on the page to show the page title.
 * @param {string} title for the page
 * @returns 
 */
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
