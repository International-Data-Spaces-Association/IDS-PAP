import { makeStyles } from "@material-ui/core";

export const useStyle = makeStyles((theme) => ({
  root: {
    flexGrow: 1,
    width: "90%",
    marginLeft: "auto",
    marginRight: "auto",
    "& .MuiFormControl-root": {
      width: "100%",
      marginBottom:"10px",
    },
    "& .MuiFormControlLabel-root": {
      width: "100%",
    },
    "& .makeStyles-pageHeader-20": {
      width: "80%",
      marginLeft: "auto",
      marginRight: "auto",

    },
    "& .MuiTypography-h5": {
      marginBottom: "1em",
    },
    "& .extraSpaceAfterTitle":{
      marginBottom:"1em",
    },
    "& .gridSubItemWithLine": {
      borderTop: "2px solid rgb(23,156,125,0.4)",
      paddingTop:theme.spacing(1),
    },
    "& .MuiGrid-container":{
      marginBottom: theme.spacing(2),
    },
},
  saveBtn: {
    height: "100%",
    width: "100%",
    fontSize: "1.25em",
  },
  addBtn: {
    width: "100%",
    fontSize: "1.25em",
  },
  paperWithoutRemoveBtn: {
    padding:"38px 20px 20px 20px",
    marginBottom:theme.spacing(2),
    borderRadius:"10px",
  },
  paper: {
    padding:"38px 0px 20px 20px",
    marginBottom:theme.spacing(3),
    borderRadius:"10px",
  },
  paperSubContainer: {
    borderStyle: "solid",
    borderColor: "#d7d7d7",
    borderWidth: "1px",
    padding: "10px",
    borderRadius: "10px",
  },
  subtitle: {
    fontSize:"1.675rem",
    fontFamily:"Roboto",
  },
  formBtn:{
    height: "55.97px",
    marginBottom: "12px"
  }
}));
