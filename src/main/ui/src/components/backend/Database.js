import axios from "axios";

function getAllPolicies(data, setData) {
  axios.get( "/api/policies").then(
    (response) => {
      console.log(response.data)
      setData(response.data);
    },
    (error) => {
      console.log(error);
    }
  );
}

function exportPolicy(id, fileName, contentType) {
  axios.get(`/api/policies/export_${id}`).then(
    (response) => {
      const a = document.createElement("a");
      const file = new Blob([JSON.stringify(response.data, null, 2)], {
        type: contentType,
      });
      a.href = URL.createObjectURL(file);
      a.download = fileName;
      a.click();
    },
    (error) => {
      console.log(error);
    }
  );
}

function deletePolicy(id, data, setData) {
  axios.delete( `/api/policies/${id}`).then(
    (response) => {
      getAllPolicies(data, setData);
    },
    (error) => {
      console.log(error);
    }
  );
}

function editPolicy(id, history) {
  axios.get( `/api/policies/edit_${id}`).then(
    (response) => {
      console.log(response.data);
      const policy = JSON.parse(response.data.fieldValues);
      policy.id = response.data.id;
      history.push({
        pathname: response.data.queryOrigin,
        state: policy,
      });
    },
    (error) => {
      console.log(error);
    }
  );
}

function viewPolicy(id, history) {
  axios.get( `/api/policies/edit_${id}`).then(
    (response) => {
      const fieldValues = JSON.parse(response.data.fieldValues);
      fieldValues.id = response.data.id
      const url = response.data.policyType
      axios.post( url, fieldValues).then(
        (response) => {
          axios
            .post(
               "/policy/initialTechnologyDependentPolicy",
              response.data
            )
            .then(
              (responseTDP) => {
                var dict = {
                  jsonPolicy: JSON.stringify(response.data, null, 2),
                  dtPolicy: responseTDP.data,
                };
                history.push({
                  pathname: "/ODRLCreator",
                  state: dict,
                });
              },
              (error) => {
                console.log(error);
              }
            );
        },
        (error) => {
          console.log(error);
        }
      );
    },
    (error) => {
      console.log(error);
    }
  );
}

export { getAllPolicies, exportPolicy, deletePolicy, editPolicy, viewPolicy };
