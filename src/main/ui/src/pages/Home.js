import React from "react";
import { makeStyles, Typography, Grid, Icon } from "@material-ui/core";
import papEditor from "../images/PAP-Editor_Scenario.png";
import { loadCSS } from 'fg-loadcss';

const useStyles = makeStyles((theme) => ({
  content: {
    fontSize: "100px",
  },
  img: {
    [theme.breakpoints.down('sm')]:{
      width: '100%',
    },
    [theme.breakpoints.up('lg')]: {
      width: '60%',
    },
    display: "block",
    marginLeft: "auto",
    marginRight: "auto",
    marginTop: "1em",
  },
  root: {
    marginBottom:'0.5em',
  },
  infoBox: {
    backgroundColor: "#179c7d",
    marginTop:'1em',
    padding:'1em',
    color:'white',
    textAlign:'justify',
  },
  text:{
    marginLeft:'1em',
    marginRight:'1em',
    lineHeight:'30px',
  },
  title:{
    [theme.breakpoints.down('sm')]:{
      textAlign:'center',
    }
  },
  link:{
    color: theme.palette.primary.main,
    textDecoration: 'none',
  }
}));
 

export default function Home() {
  const classes = useStyles();

  React.useEffect(() => {
    const node = loadCSS(
      'https://use.fontawesome.com/releases/v5.12.0/css/all.css',
      document.querySelector('#font-awesome-css'),
    );

    return () => {
      node.parentNode.removeChild(node);
    };
  }, []);

  return (
    <div className={classes.root}>
      <div className={classes.text}>
        <Typography variant="h3" className={classes.title}> Data Usage Control Scenario </Typography>
        <img className={classes.img} src={papEditor} alt=""></img>
        <Typography variant="body1">
          An IDS customer can be a Data Provider or a Data Consumer.
          There are variety of Data Usage Control policies that can be defined
          by data providers to protect their data.
          The Open Digital Rights Language (
          <a href="https://www.w3.org/TR/odrl-model/" className={classes.link}>ODRL</a>) is a policy
          expression language that provides a flexible and interoperable
          information model, vocabulary, and encoding mechanisms for
          representing statements about the usage of content and services.
        </Typography>
        <Typography variant="body1">
          <a href="https://www.w3.org/TR/odrl-model/" className={classes.link}>ODRL</a> policies are
          technology-independent and are not made to be technically enforced to
          a system. Therefore, it is been decided to use this language as a
          baseline for other technology-dependent solutions that can enforce
          Data Usage Control policies into the systems to protect data.
          One of the technology-dependent policy languages and platforms
          provided by{" "}
          <a href="https://www.iese.fraunhofer.de/" className={classes.link}>Fraunhofer IESE</a> is{" "}
          <a href="https://developer.mydata-control.de/" className={classes.link}>MYDATA</a>.{" "}
          <a href="https://developer.mydata-control.de/" className={classes.link}>MYDATA</a> is a
          solution that intercepts events or data flows in a system and enforces
          a security decision based on{" "}
          <a href="https://developer.mydata-control.de/" className={classes.link}>MYDATA</a> policies.
        </Typography>
        <br />
        <Typography variant="h5">PAP Editor</Typography>
        <Typography variant="body1">
          In this tool, we have implemented an editor that supports the data
          providers to specify their Data Usage Control{" "}
          <a href="https://www.w3.org/TR/odrl-model/" className={classes.link}>ODRL</a> policies.
          In addition, they can use this tool to translate the{" "}
          <a href="https://www.w3.org/TR/odrl-model/" className={classes.link}>ODRL</a> policies to{" "}
          <a href="https://developer.mydata-control.de/" className={classes.link}>MYDATA</a> policies
        </Typography>
        <br />
        <Typography variant="h5">
          Click{" "}
          <u>
            <a href="/policy/HowTo" className={classes.link}>here</a>
          </u>{" "}
          to learn how to use this editor!
        </Typography>
      </div>
      <div className={classes.infoBox}>
        <Grid container spacing={1}>
          <Grid item xs={2} md={1} container justify="center">
            <Icon className="fas fa-question" style={{ color: '#FFFFFF', fontSize:"4em",}} />
          </Grid>
          <Grid item xs={10} md={10}>
            <Typography variant="body1">
              {" "}
              Please check our page on{" "}
              <a href="https://www.ids.isst.fraunhofer.de/confluence/pages/viewpage.action?spaceKey=FDS&title=T8+-+Work+Content" style={{color:'#FFFFFF'}}>
                IDS confluence
              </a>{" "}
              to get more information about the policy transformation rules,
              ODRL and MYDATA policy specification and IDS specific profile.{" "}
              
              IDS profile includes information model about the IDS Actions and
              IDS Left Operands.
              
              If you have any further questions, please do not hesitate to{" "}
              <a href="https://www.mydata-control.de/#contact" style={{color:'#FFFFFF'}}>Contact us</a>.
            </Typography>
          </Grid>
        </Grid>
      </div>
    </div>
  );
}
