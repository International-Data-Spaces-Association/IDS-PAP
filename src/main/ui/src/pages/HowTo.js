import React from "react";
import { makeStyles, Typography, Grid, Icon } from "@material-ui/core";
import papEditor from "../images/PAP-Editor_Scenario.png";
import { loadCSS } from "fg-loadcss";

const useStyles = makeStyles((theme) => ({
  content: {
    fontSize: "100px",
  },
  img: {
    [theme.breakpoints.down("sm")]: {
      width: "100%",
    },
    [theme.breakpoints.up("lg")]: {
      width: "60%",
    },
    display: "block",
    marginLeft: "auto",
    marginRight: "auto",
    marginTop: "1em",
  },
  root: {
    marginBottom: "0.5em",
  },
  infoBox: {
    backgroundColor: "#F39C12E0",
    marginTop: "1em",
    padding: "1em",
    color: "white",
    textAlign: "justify",
  },
  text: {
    marginLeft: "1em",
    marginRight: "1em",
    lineHeight: "30px",
  },
  title: {
    [theme.breakpoints.down("sm")]: {
      textAlign: "center",
    },
  },
  link: {
    color: theme.palette.primary.main,
    textDecoration: "none",
  },
  icon: {
    textAlign: 'center',
  },
}));

export default function HowTo() {
  const classes = useStyles();

  React.useEffect(() => {
    const node = loadCSS(
      "https://use.fontawesome.com/releases/v5.12.0/css/all.css",
      document.querySelector("#font-awesome-css")
    );

    return () => {
      node.parentNode.removeChild(node);
    };
  }, []);

  return (
    <div className={classes.root}>
      <div className={classes.text}>
        <Typography variant="h3" className={classes.title}>
          How To
        </Typography>
        <img className={classes.img} src={papEditor} alt=""></img>
        <Typography variant="body1">
          <li>
            As an IDS Data Provider, you can choose a policy class from the side
            bar of the tool and then, follow the instructions.
          </li>
        </Typography>
        <Typography variant="body1">
          <li>
            By pressing the "Save" button, you will get the corresponding <a href="https://www.w3.org/TR/odrl-model/" className={classes.link}>ODRL
            </a> policy.
          </li>
        </Typography>
        <Typography variant="body1">
          <li>
            In order to get the <a href="https://developer.mydata-control.de/" className={classes.link}>MYDATA
            </a> policy, you can press the "Transfer"
            button.
          </li>
        </Typography>
        <Typography variant="body1">
          <li>
            In addition, you can export the <a href="https://developer.mydata-control.de/" className={classes.link}>MYDATA
            </a> policy from this editor.
          </li>
        </Typography>
        <Typography variant="body1">
          <li>
            This exported <a href="https://developer.mydata-control.de/" className={classes.link}>MYDATA
            </a> policy is ready to be imported into the <a href="https://developer.mydata-control.de/" className={classes.link}>MYDATA
            </a> PMP (Policy Management Point). You can then deploy the <a href="https://developer.mydata-control.de/" className={classes.link}>MYDATA
            </a> policy.
          </li>
        </Typography>

      </div>
      <div className={classes.infoBox}>
        <Grid container spacing={0}  direction="row" alignItems="center" justify="center">
          <Grid item xs={2} md={1} container>
            <Icon
              className="fas fa-exclamation"
              style={{ color: "#FFFFFF", fontSize: "5em" }}
            />
          </Grid>
          <Grid item xs={8} md={9}>
            <Typography variant="body1">
              Be aware that deploying <a href="https://developer.mydata-control.de/" className={classes.link}>MYDATA
            </a> policy into the <a href="https://developer.mydata-control.de/" className={classes.link}>MYDATA
            </a> PDP (Policy
              Decision Point) is not enough for the policy enforcement. You
              still need to create <a href="https://developer.mydata-control.de/" className={classes.link}>MYDATA
            </a> PEP (Policy Enforcement Point) and the
              needed <a href="https://developer.mydata-control.de/" className={classes.link}>MYDATA
            </a> PIPs (Policy Information Points) and <a href="https://developer.mydata-control.de/" className={classes.link}>MYDATA
            </a> PXPs
              (Policy Execution Points). When the enforcement point is
              implemented, then you can be assured that your data is technically
              protected. To implement your <a href="https://developer.mydata-control.de/" className={classes.link}>MYDATA
            </a> PEP (Policy Enforcement
              Point), follow the instructions documented in <a href="https://developer.mydata-control.de/sdk/howto_pep.html" className={classes.link}>MYDATA Web Page
            </a>.
            </Typography>
          </Grid>
          <Grid item xs={2} md={1} container>
          </Grid>
        </Grid>
      </div>
    </div>
  );
}
