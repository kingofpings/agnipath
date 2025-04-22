
pipeline {
    agent {label "self"
          }//end of agent

          environment {

          OPEN_AI_KEY =   credentials('OPEN_API_KEY')
        
        }

    stages {
        stage('Deploy_dvwa-llm') {
            steps {
                echo 'Deploying dvwa LLM'
                sh '''
                    cd dvwa-llm
                    pwd
                    whoami
                    ls -alh
                    apt install python3
                    python3 --version
                    python3 -m venv env
                    source env/bin/activate
                    pip install -r requirements.txt
                    pipenv install python-dotenv
                
                '''
                echo 'Starting dvwa-llm'
                sh '''
                    cd dvwa-llm
                    streamlit run main.py
                
                '''

                echo 'Buidling dvwa-llm docker image'
                sh '''
                    cd dvwa-llm
                    docker build -t dvla .
                    docker run --env-file env.list -p 8501:8501 dvla
                
                '''

            } //end of steps
        } //end of stage build

    } //end of stages
} //end of pipeline