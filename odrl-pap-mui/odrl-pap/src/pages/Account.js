import React from "react";
import { makeStyles, Avatar, Grid, TextField, Button } from "@material-ui/core";

import profile_img from "../images/John.jpg";

const useStyles = makeStyles((theme) => ({
  profileImg: {
    width: "15%",
    height: "15%",
    marginLeft: "auto",
    marginRight: "auto",
    marginBottom: '1em',
  },
  page: {
    width: "80%",
    marginLeft: "auto",
    marginRight: "auto",
  },
}));

export default function Account() {
  const classes = useStyles();
  return (
    <div className={classes.page}>
      <Avatar src={profile_img} className={classes.profileImg} />
      <Grid container spacing={3} alignItems="center" justify="center" direction="column">
        <Grid item xs={12}>
          <TextField
            name="nameField"
            label="Name"
            value="John Voss"
            variant="outlined"
            InputLabelProps={{ shrink: true }}
          />
        </Grid>
        <Grid item xs={12}>
          <TextField
            name="emailField"
            label="Email"
            value="john.voss@fraunhofer.de"
            variant="outlined"
            InputLabelProps={{ shrink: true }}
          />
        </Grid>
        <Grid item xs={12}>
          <Button variant="contained" color="primary" type="submit">
            Save
          </Button>
        </Grid>
      </Grid>
    </div>
  );
}
